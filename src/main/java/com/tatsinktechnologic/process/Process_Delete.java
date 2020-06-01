/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.process;

import com.tatsinktech.process.beans.Process_Request;
import com.tatsinktechnologic.beans_entity.Command;
import com.tatsinktechnologic.beans_entity.Mo_Hist;
import com.tatsinktechnologic.beans_entity.Product;
import com.tatsinktechnologic.beans_entity.Register;
import com.tatsinktechnologic.dao_commun_repository.CommunRepository;
import com.tatsinktechnologic.dao_repository.Mo_HistJpaController;
import com.tatsinktechnologic.dao_repository.RegisterJpaController;
import com.tatsinktechnologic.loadconfig.Load_Configuration;
import com.tatsinktech.process.thread.sender.Sender;
import com.tatsinktech.process.util.Utils;
import com.tatsinktechnologic.xml.Application;
import com.tatsinktechnologic.xml.ProcessThread_Reg;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import javax.persistence.EntityManagerFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author olivier.tatsinkou
 */
public class Process_Delete implements Runnable{
    private static Logger logger = Logger.getLogger(Process_Delete.class);
    
     private static Load_Configuration commonConfig = Load_Configuration.getConfigurationLoader();
    
    private static EntityManagerFactory emf;
    private static int sleep_duration;
    private static Application app_conf;
    private static ProcessThread_Reg processthead_reg;
    private static InetAddress address;
    private static HashMap<String,Command> SETCOMMAND;
    private static HashMap<String,Product> SETPRODUCT;
    private CommunRepository communRepo;
  
    private static BlockingQueue<Process_Request> delete_queue;


    public static void addMo_Queue(Process_Request process_req){
        try {
            delete_queue.put(process_req);
            logger.info("ADD message in the queue :"+ process_req);
        } catch (InterruptedException e) {
            logger.error("Error to add in reg_queue :"+ process_req,e);
        }

    }
  


    public Process_Delete(EntityManagerFactory emf,BlockingQueue<Process_Request> delete_queue,int sleep_duration) {
        this.emf = commonConfig.getEmf();
        this.delete_queue=delete_queue;
        this.app_conf = commonConfig.getApp_conf();
        this.processthead_reg=app_conf.getProcess_thread_reg();
        this.sleep_duration= processthead_reg.getSleep_duration();
        SETCOMMAND = commonConfig.getSETCOMMAND();
        SETPRODUCT = commonConfig.getSETPRODUCT();
        address = Utils.gethostName();
        communRepo = new CommunRepository(emf);
    }
    
    @Override
    public void run() {
       
        logger.info("################################## START PROCESS DELETE ###########################");
        while (true){
            // Removing an element from the Queue using poll()
            // The poll() method returns null if the Queue is empty.
            Process_Request process_mo = null;
             try{
                //consuming messages 
                process_mo = delete_queue.take();
                logger.info("Get message in Delete queue :"+ process_mo);
                logger.info("Delete Queue size :"+ delete_queue.size());
            } catch (InterruptedException e) {
                logger.error("Error to Get in reg_queue :"+ process_mo,e);
            }

             
            if (process_mo != null){
                String msisdn = process_mo.getMsisdn();
                String transaction_id = process_mo.getTransaction_id();
                String product_code = process_mo.getProduct_name();
                Timestamp receive_time = process_mo.getReceive_time();
                String mo_his_desc = "";
                
                if (!StringUtils.isBlank(product_code)){
                    Product product =  SETPRODUCT.get(product_code);
                                                           
                     Register reg = communRepo.getRegister(msisdn, product);
                        if (reg==null){
                           process_mo.setNotification_code("DEL-PRODUCT-NOT-REGISTER-"+product_code);
                           mo_his_desc="CUSTOMER NOT REGISTER TO OFFER";
                        }else {
                           reg.setStatus(0);
                           reg.setUnreg_time(receive_time);
                           RegisterJpaController regCont = new RegisterJpaController(emf);
                           try {
                                     regCont.edit(reg);
                                     process_mo.setNotification_code("DEL-PRODUCT-SUCCESS-"+product_code);
                                     mo_his_desc="SUCCESS DELETE PRODUCT";
                                } catch (Exception e) {
                                    logger.error("Customer don't have account of this Offer. Cannot Edit",e);
                                }
                        }
                    
                }else{
                    process_mo.setNotification_code("DEL-PRODUCT-NOT-EXIST");
                    mo_his_desc="PRODUCT NOT EXIST";
               } 
                
               // send to sender
               Sender.addMo_Queue(process_mo);

               Command cmd = SETCOMMAND.get(process_mo.getCommand_code());
               Mo_Hist mo_hist = new Mo_Hist();
               mo_hist.setAction_type(process_mo.getAction_type());
               mo_hist.setChannel(process_mo.getChannel());
               mo_hist.setContent(process_mo.getContent());
               mo_hist.setMsisdn(msisdn);
               mo_hist.setReceive_time(process_mo.getReceive_time());
               mo_hist.setCommand(cmd);
               mo_hist.setTransaction_id(transaction_id);
               mo_hist.setProcess_unit("Process_Delete");
               mo_hist.setIP_unit(address.getHostName()+"@"+address.getHostAddress());
               mo_hist.setError_description(mo_his_desc);

               Mo_HistJpaController mo_histController = new Mo_HistJpaController(emf);
               mo_histController.create(mo_hist);

               logger.info("insert into mo_his");

            }
            
          

            try {
                Thread.sleep(sleep_duration);
            } catch (Exception e) {
            }
            
        }
        
        
    }
     public static void executeRunnables(final ExecutorService service, List<Runnable> runnables){
                //On exécute chaque "Runnable" de la liste "runnables"
		for(Runnable r : runnables){

			service.execute(r);
		}
		service.shutdown();
	}
}
