///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tatsinktechnologic.process;
//
//import com.tatsinktech.process.beans.Process_Request;
//import com.tatsinktechnologic.beans_entity.Command;
//import com.tatsinktechnologic.beans_entity.Mo_Hist;
//import com.tatsinktechnologic.beans_entity.Product;
//import com.tatsinktechnologic.beans_entity.Register;
//import com.tatsinktechnologic.dao_commun_repository.CommunRepository;
//import com.tatsinktechnologic.dao_repository.Mo_HistJpaController;
//import com.tatsinktechnologic.loadconfig.Load_Configuration;
//import com.tatsinktech.process.thread.sender.Sender;
//import com.tatsinktech.process.util.Utils;
//import com.tatsinktechnologic.xml.Application;
//import com.tatsinktechnologic.xml.ProcessThread_Reg;
//import java.net.InetAddress;
//import java.sql.Timestamp;
//import java.util.HashMap;
//import java.util.List;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.ExecutorService;
//import javax.persistence.EntityManagerFactory;
//import org.apache.log4j.Logger;
//
///**
// *
// * @author olivier.tatsinkou
// */
//public class Process_Del_ChatGroup implements Runnable{
//        private static Logger logger = Logger.getLogger(Process_Del_ChatGroup.class);
//    
//    private static Load_Configuration commonConfig = Load_Configuration.getConfigurationLoader();
//    
//    private static EntityManagerFactory emf;
//    private static int sleep_duration;
//    private static Application app_conf;
//    private static ProcessThread_Reg processthead_reg;
//    private static InetAddress address;
//    private static HashMap<String,Command> SETCOMMAND;
//    private static HashMap<String,Product> SETPRODUCT;
//    private CommunRepository communRepo;
//  
//    private static BlockingQueue<Process_Request> account_queue;
//
//
//    public static void addMo_Queue(Process_Request process_req){
//        try {
//            account_queue.put(process_req);
//            logger.info("ADD message in the queue :"+ process_req);
//        } catch (InterruptedException e) {
//            logger.error("Error to add in reg_queue :"+ process_req,e);
//        }
//
//    }
//
//    public Process_Del_ChatGroup(EntityManagerFactory emf,BlockingQueue<Process_Request> account_queue,int sleep_duration) {
//        this.emf = commonConfig.getEmf();
//        this.account_queue=account_queue;
//        this.app_conf = commonConfig.getApp_conf();
//        this.processthead_reg=app_conf.getProcess_thread_reg();
//        this.sleep_duration= processthead_reg.getSleep_duration();
//        this.SETCOMMAND = commonConfig.getSETCOMMAND();
//        this.SETPRODUCT = commonConfig.getSETPRODUCT();
//        this.address = Utils.gethostName();
//        this.communRepo=new CommunRepository(emf);
//    }
//    
//    @Override
//    public void run() {
//       
//        logger.info("################################## START PROCESS ACCOUNT ###########################");
//        while (true){
//            // Removing an element from the Queue using poll()
//            // The poll() method returns null if the Queue is empty.
//            Process_Request process_mo = null;
//             try{
//                //consuming messages 
//                process_mo = account_queue.take();
//                logger.info("Get message in Check queue :"+ process_mo);
//                logger.info("Check Queue size :"+ account_queue.size());
//            } catch (InterruptedException e) {
//                logger.error("Error to Get in chek_queue :"+ process_mo,e);
//            }
//
//             
//            if (process_mo != null){
//                String msisdn = process_mo.getMsisdn();
//                String transaction_id = process_mo.getTransaction_id();
//                String product_code = process_mo.getProduct_name();
//                Timestamp receive_time = process_mo.getReceive_time();
//                String mo_his_desc = "";
//                
//                if (product_code!= null){
//                    Product product =  SETPRODUCT.get(product_code);
//                                       
//                    // get live duration of offer
//                    Timestamp prod_start_date = product.getStart_date();
//                    Timestamp prod_end_date = product.getEnd_date();
//                    
//                    int useproduct = 0;
//                   
//                    if ( prod_start_date!=null && prod_end_date!= null ){
//                           if (prod_start_date.after(prod_end_date)){
//                                useproduct = 1;               // start time is after end time. wrong time configuration
//                            }else{
//                                  if (prod_start_date.after(receive_time)) {
//                                     useproduct = 2;            // start time is after receive time customer cannot register to product. product not available.
//                                  }
//                                  if (prod_end_date.before(receive_time)) {
//                                     useproduct = 3;            // end time is before receive time customer cannot register to product. product is expire
//                                  }
//                           }
//                    }else if (prod_start_date!=null){
//                         if (prod_start_date.after(receive_time)) {
//                                     useproduct = 2;            // start time is after receive time customer cannot register to product. product not available.
//                          }
//                    }else if (prod_end_date!=null){
//                        if (prod_end_date.before(receive_time)) {
//                                     useproduct = 3;            // end time is before receive time customer cannot register to product. product is expire
//                        }
//                    }
//
//               switch(useproduct){
//                    case 0:
//                        Register reg = communRepo.getRegister(msisdn, product);
//                        if (reg==null){
//                           process_mo.setNotification_code("CHECK-PRODUCT-NOT-REGISTER-"+product_code);
//                           mo_his_desc="CUSTOMER NOT REGISTER TO OFFER";
//                        }else {
//                           process_mo.setNotification_code("CHECK-PRODUCT-SUCCESS-"+product_code);
//                           mo_his_desc="SUCCESS CHECK PRODUCT";
//                        }
//                        break;
//                    case 1:
//                        process_mo.setNotification_code("CHECK-PRODUCT-WRONG-TIME-"+product_code);
//                        mo_his_desc="WRONG TIME CONFIGURATION OF THIS PRODUCT";
//                        break;
//                    case 2:
//                        process_mo.setNotification_code("CHECK-PRODUCT-NOT-AVAILABLE-"+product_code);
//                        mo_his_desc="PRODUCT IS NOT AVAILABLE";
//                        break;
//                    case 3:
//                        process_mo.setNotification_code("CHECK-PRODUCT-EXPIRE-"+product_code);
//                        mo_his_desc="PRODUCT IS EXPIRE";
//                        break;
//
//                } 
//                    
//
//                }else{
//                    process_mo.setNotification_code("CHECK-PRODUCT-NOT-EXIST");
//                    mo_his_desc="PRODUCT NOT EXIST";
//               } 
//                
//               // send to sender
//               Sender.addMo_Queue(process_mo);
//
//               Command cmd = SETCOMMAND.get(process_mo.getCommand_code());
//               Mo_Hist mo_hist = new Mo_Hist();
//               mo_hist.setAction_type(process_mo.getAction_type());
//               mo_hist.setChannel(process_mo.getChannel());
//               mo_hist.setContent(process_mo.getContent());
//               mo_hist.setMsisdn(msisdn);
//               mo_hist.setReceive_time(process_mo.getReceive_time());
//               mo_hist.setCommand(cmd);
//               mo_hist.setTransaction_id(transaction_id);
//               mo_hist.setProcess_unit("Process_Check");
//               mo_hist.setIP_unit(address.getHostName()+"@"+address.getHostAddress());
//               mo_hist.setError_description(mo_his_desc);
//
//               Mo_HistJpaController mo_histController = new Mo_HistJpaController(emf);
//               mo_histController.create(mo_hist);
//
//               logger.info("insert into mo_his");
//
//            }
//            
//            try {
//                Thread.sleep(sleep_duration);
//            } catch (Exception e) {
//            }
//            
//        }
//        
//        
//    }
//     public static void executeRunnables(final ExecutorService service, List<Runnable> runnables){
//                //On exécute chaque "Runnable" de la liste "runnables"
//		for(Runnable r : runnables){
//
//			service.execute(r);
//		}
//		service.shutdown();
//	}
//}
