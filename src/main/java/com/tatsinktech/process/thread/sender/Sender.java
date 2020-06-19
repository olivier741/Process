/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.process.thread.sender;

import com.tatsinktech.process.kafka.service.MyKafkaProducer;
import com.tatsinktech.process.model.register.Notification_Conf;
import com.tatsinktech.process.beans.Message_Exchg;
import com.tatsinktech.process.beans.Process_Request;
import com.tatsinktech.process.config.Load_Configuration;
import com.tatsinktech.process.model.register.Mt_Hist;
import com.tatsinktech.process.model.repository.Mt_HistRepository;
import com.tatsinktech.process.util.ConverterXML_JSON;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import javax.annotation.PostConstruct;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author olivier
 */
@Component
public class Sender implements Runnable {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private HashMap<String, Notification_Conf> notification;
    private int sleep_duration;
    private HashMap<String, String> SETVARIABLE;
    private InetAddress address;

    private static BlockingQueue<Process_Request> send_queue;

    @Autowired
    private Load_Configuration commonConfig;

    @Autowired
    private MyKafkaProducer myKafkaProducer;

    @Autowired
    private Mt_HistRepository mthistRepo;

    public static void addMo_Queue(Process_Request process_req) {
        try {
            send_queue.put(process_req);
//            logger.info("ADD message in the queue :" + process_req);
        } catch (InterruptedException e) {
//            logger.error("Error to add in reg_queue :" + process_req, e);
        }

    }

    @PostConstruct
    private void init() {
        this.notification = commonConfig.getSETNOTIFICATION();
        this.sleep_duration = Integer.parseInt(commonConfig.getApplicationSenderSleepDuration());
        address = gethostName();
    }

    public static void setSend_queue(BlockingQueue<Process_Request> send_queue) {
        Sender.send_queue = send_queue;
    }

    @Override
    public void run() {

        logger.info("################################## START SENDER ###########################");
        List<String> listLebelNotif = Arrays.asList("REG-PRODUCT-NOT-EXIST",
                "CHECK-PRODUCT-NOT-EXIST",
                "DEL-PRODUCT-NOT-EXIST",
                "GUIDE-PRODUCT-NOT-EXIST",
                "RECEIVER-ACTION-NOT-DEFINE",
                "RECEIVER-NOT-ACTION-TYPE",
                "RECEIVER-WRONG-SYNTAX"
        );
        while (true) {
            // Removing an element from the Queue using poll()
            // The poll() method returns null if the Queue is empty.
            Process_Request process_mo = null;
            try {
                //consuming messages 
                synchronized (this) {
                    logger.info("Sender Queue size :" + send_queue.size());
                    process_mo = send_queue.take();
                }
                logger.info("Get message in the sender queue :" + process_mo);

            } catch (InterruptedException e) {
                logger.error("Error to Get in reg_queue :" + process_mo, e);
            }

            if (process_mo != null) {

                String transactionid = process_mo.getTransaction_id();
                String receiver = process_mo.getMsisdn();
                String nofif_code = process_mo.getNotificationCode();
                String sender = process_mo.getSendChannel().trim().toUpperCase();

                if (listLebelNotif.contains(nofif_code)) {
                    Notification_Conf notif_conf_chanel = notification.get("DEFAULT-CHANNEL");
                    if (!StringUtils.isBlank(notif_conf_chanel.getNotificationValue())) {
                        sender = notif_conf_chanel.getNotificationValue();
                    }
                }

                String message = nofif_code;
                SETVARIABLE = process_mo.getSetvariable();
                if (!StringUtils.isBlank(nofif_code)) {
                    Notification_Conf notif_conf = notification.get(nofif_code);
                    JSONObject jsonObject = new JSONObject();

                    if (notif_conf != null) {
                        message = notif_conf.getNoficationName();
                        if (notif_conf.getNotificationValue() != null) {
                            message = notif_conf.getNotificationValue();
                        }

                        if (SETVARIABLE != null && !SETVARIABLE.isEmpty()) {
                            for (Map.Entry<String, String> variable : SETVARIABLE.entrySet()) {
                                if (variable.getValue() != null) {
                                    message = message.replace(variable.getKey(), variable.getValue());
                                }
                            }
                        }

                        try {
                            jsonObject.put("transaction_id", transactionid);
                            jsonObject.put("service_id", process_mo.getServiceName());
                            jsonObject.put("message_id", "message_1");
                            jsonObject.put("sender", sender);
                            jsonObject.put("receiver", receiver);
                            jsonObject.put("content", message);
                            jsonObject.put("exchange_mode", "SENDER");

                        } catch (JSONException e) {
                            logger.error(e.getMessage());
                        }
                        logger.info("Msisdn :" + receiver + " --> Channel : " + sender + " --> Notification Code : " + nofif_code + " --> Message :  " + message);
                    } else {
                        try {
                            jsonObject.put("transaction_id", transactionid);
                            jsonObject.put("service_id", process_mo.getServiceName());
                            jsonObject.put("message_id", "message_1");
                            jsonObject.put("sender", sender);
                            jsonObject.put("receiver", receiver);
                            jsonObject.put("content", message);
                            jsonObject.put("exchange_mode", "SENDER");

                        } catch (JSONException e) {
                            logger.error(e.getMessage());
                        }

                        logger.warn("Msisdn :" + receiver + " --> Channel : " + sender + " --> Notification Code : " + nofif_code + " --> Message Not provide in DB");
                    }
                    myKafkaProducer.sendDataToKafka(jsonObject.toString());
                } else {
                    logger.warn("Msisdn :" + receiver + " --> Channel : " + sender + " --> Not Notification Code  Provided ");
                }
                Timestamp sendTime = new Timestamp(System.currentTimeMillis());
                Mt_Hist mtHist = new Mt_Hist();
                mtHist.setMsisdn(receiver);
                mtHist.setChannel(sender);
                mtHist.setCommandCode(process_mo.getCommanCode().trim().toUpperCase());
                mtHist.setCommandName(process_mo.getCommandName().trim().toUpperCase());
                mtHist.setDescription(process_mo.getNotificationCode());
                mtHist.setIpAddress(address.getHostName() + "@" + address.getHostAddress());
                mtHist.setMessage(message);
                if (process_mo.getParamName() != null) {
                    mtHist.setParamName(process_mo.getParamName().trim().toUpperCase());
                }
                mtHist.setProcessUnit("Sender");
                mtHist.setProductCode(process_mo.getProductCode().trim().toUpperCase());
                mtHist.setReceiveTime(process_mo.getReceiveTime());
                mtHist.setSendTime(sendTime);
                mtHist.setServiceName(process_mo.getServiceName().trim().toUpperCase());
                mtHist.setTransactionId(process_mo.getTransaction_id());

                mthistRepo.save(mtHist);

            } else {
                logger.warn("Process Request is NULL ");
            }

            try {
                Thread.sleep(sleep_duration);
            } catch (Exception e) {
            }

        }

    }

    private InetAddress gethostName() {

        InetAddress addr = null;

        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            logger.error("Hostname can not be resolved", ex);
        }
        return addr;
    }

    public static void executeRunnables(final ExecutorService service, List<Runnable> runnables) {
        //On exécute chaque "Runnable" de la liste "runnables"
        for (Runnable r : runnables) {

            service.execute(r);
        }
        service.shutdown();
    }
}
