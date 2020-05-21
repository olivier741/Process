/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.resfull.client;

import com.tatsinktechnologic.beans_entity.Product;
import com.tatsinktechnologic.beans_entity.Service;
import java.io.File;
import java.sql.Timestamp;
import java.util.Calendar;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author olivier.tatsinkou
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
        private static Logger logger = Logger.getLogger(NewMain.class);
        
    public static void main(String[] args) {
        // TODO code application logic here
//        PropertyConfigurator.configure("etc" + File.separator + "log4j.cfg");
//        logger.info("Load log4j config file done.");
//        Service serv=new Service();
//        serv.setService_name("service1");
//        Product product = new Product();
//        product.setProduct_id(1L);
//        product.setProduct_code("Dialo");
//        product.setReg_fee(10);
//        product.setService(serv);
//        Webservice_Request ws_request = new Webservice_Request();
//        ws_request.requestCharge("237661000504","1111111111111222222", product);
        
        Timestamp receive_time = new Timestamp(System.currentTimeMillis());
        
        System.out.println(receive_time);
        System.out.println(getExpire_Time("D30",receive_time));
        System.out.println(getExpire_Time("H5",receive_time));
        System.out.println(getExpire_Time("H24",receive_time));
        
    }
    
    
    private static Timestamp getExpire_Time(String validity,Timestamp current_time){
        Timestamp result = current_time;
        
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(current_time.getTime());
        
        if (validity.toUpperCase().startsWith("D")){
            String value = validity.replace("D", "");
            try {
               int nbDay = Integer.parseInt(value);
               cal.add(Calendar.DAY_OF_MONTH, nbDay);
               result = new Timestamp(cal.getTime().getTime());
            } catch (Exception e) {
//                result = -1;
            }
        }else if (validity.toUpperCase().startsWith("H")){
            String value = validity.replace("H", "");
            try {
               int nbHour = Integer.parseInt(value);
               cal.add(Calendar.HOUR_OF_DAY, nbHour);
               result = new Timestamp(cal.getTime().getTime());
            } catch (Exception e) {
//                result = -1;
            }
        }
        return result;
    }
    
}
