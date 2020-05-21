/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.thread.sender;

import com.tatsinktech.kafka.service.MyKafkaProducer;
import com.tatsinktech.model.register.Notification_Conf;
import com.tatsinktech.beans.Message_Exchg;
import com.tatsinktech.beans.Process_Request;
import com.tatsinktech.config.Load_Configuration;
import com.tatsinktech.util.ConverterXML_JSON;
import com.tatsinktechnologic.xml.Application;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author olivier
 */
public class Sender implements Runnable {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private static Load_Configuration commonConfig = Load_Configuration.getConfigurationLoader();
    private HashMap<String, Notification_Conf> notification;
    private int sleep_duration;
    private Application app_conf;
    private HashMap<String, String> SETVARIABLE;

    private static BlockingQueue<Process_Request> send_queue;

    @Autowired
    private MyKafkaProducer myKafkaProducer;

    public static void addMo_Queue(Process_Request process_req) {
        try {
            send_queue.put(process_req);
//            logger.info("ADD message in the queue :" + process_req);
        } catch (InterruptedException e) {
//            logger.error("Error to add in reg_queue :" + process_req, e);
        }

    }

    public Sender() {
        this.notification = commonConfig.getSETNOTIFICATION();
        this.app_conf = commonConfig.getApp_conf();
    }

    public void setSleep_duration(int sleep_duration) {
        this.sleep_duration = sleep_duration;
    }

    public static void setSend_queue(BlockingQueue<Process_Request> send_queue) {
        Sender.send_queue = send_queue;
    }

    @Override
    public void run() {

        logger.info("################################## START SENDER ###########################");

        while (true) {
            // Removing an element from the Queue using poll()
            // The poll() method returns null if the Queue is empty.
            Process_Request process_mo = null;
            try {
                //consuming messages 
                process_mo = send_queue.take();
                logger.info("Get message in the sender queue :" + process_mo);
                logger.info("Sender Queue size :" + send_queue.size());
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "Error to Get in reg_queue :" + process_mo, e);
            }

            if (process_mo != null) {
                String receiver = process_mo.getMsisdn();
                String sender = process_mo.getSendChannel();
                String nofif_code = process_mo.getNotificationCode();
                SETVARIABLE = process_mo.getSetvariable();
                if (!StringUtils.isBlank(nofif_code)) {
                    Notification_Conf notif_conf = notification.get(nofif_code);
                    String message = "";

                    if (notif_conf != null) {
                        message = notif_conf.getNotificationValue();

                        for (Map.Entry<String, String> variable : SETVARIABLE.entrySet()) {
                            if (variable.getValue() != null) {
                                message = message.replace(variable.getKey(), variable.getValue());
                            }
                        }

                        Message_Exchg msg_exch = new Message_Exchg(null, null, null, sender, receiver, message, null);
                        String message_send = ConverterXML_JSON.convertMsgExchToJson(msg_exch);
                        myKafkaProducer.sendDataToKafka(message_send);
                        logger.info("Msisdn :" + receiver + " --> Channel : " + sender + " --> Notification Code : " + nofif_code + " --> Message :  " + message);
                    } else {
                        logger.log(Level.WARNING, "Msisdn :" + receiver + " --> Channel : " + sender + " --> Notification Code : " + nofif_code + " --> Message Not provide in DB");
                    }

                } else {
                    logger.log(Level.WARNING, "Msisdn :" + receiver + " --> Channel : " + sender + " --> Not Notification Code  Provided ");
                }

            } else {
                logger.log(Level.WARNING, "Process Request is NULL ");
            }

            try {
                Thread.sleep(sleep_duration);
            } catch (Exception e) {
            }

        }

    }

    public static void executeRunnables(final ExecutorService service, List<Runnable> runnables) {
        //On exécute chaque "Runnable" de la liste "runnables"
        for (Runnable r : runnables) {

            service.execute(r);
        }
        service.shutdown();
    }
}
