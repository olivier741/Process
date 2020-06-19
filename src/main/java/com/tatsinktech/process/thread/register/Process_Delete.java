/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.process.thread.register;

import com.tatsinktech.process.beans.Process_Request;
import com.tatsinktech.process.config.Load_Configuration;
import com.tatsinktech.process.model.register.Mo_Hist;
import com.tatsinktech.process.model.register.Product;
import com.tatsinktech.process.model.register.Register;
import com.tatsinktech.process.model.repository.Mo_HistRepository;
import com.tatsinktech.process.model.repository.RegisterRepository;
import com.tatsinktech.process.thread.sender.Sender;
import com.tatsinktech.process.util.Utils;
import java.net.InetAddress;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author olivier.tatsinkou
 */
@Component
public class Process_Delete implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(Process_Delete.class);

    private static int sleep_duration;
    private static InetAddress address;
    private static HashMap<String, Product> SETPRODUCT;

    private static BlockingQueue<Process_Request> delete_queue;

    @Autowired
    private RegisterRepository registerRepo;

    @Autowired
    private Mo_HistRepository mohisRepo;

    @Autowired
    private Load_Configuration commonConfig;
    
    public static void setDelete_queue(BlockingQueue<Process_Request> delete_queue) {
        Process_Delete.delete_queue = delete_queue;
    }

    public static void addMo_Queue(Process_Request process_req) {
        try {
            delete_queue.put(process_req);
            logger.info("ADD message in the queue :" + process_req);
        } catch (InterruptedException e) {
            logger.error("Error to add in reg_queue :" + process_req, e);
        }

    }

      @PostConstruct
    private void init() {
        Process_Delete.sleep_duration = Integer.parseInt(commonConfig.getApplicationProcessDelSleepDuration());
        Process_Delete.SETPRODUCT = commonConfig.getSETPRODUCT();
        Process_Delete.address = Utils.gethostName();
    }

    @Override
    public void run() {

        logger.info("################################## START PROCESS DELETE ###########################");
        while (true) {
            // Removing an element from the Queue using poll()
            // The poll() method returns null if the Queue is empty.
            Process_Request process_mo = null;
            try {
                //consuming messages 
                process_mo = delete_queue.take();
                logger.info("Get message in Delete queue :" + process_mo);
                logger.info("Delete Queue size :" + delete_queue.size());
            } catch (InterruptedException e) {
                logger.error("Error to Get in reg_queue :" + process_mo, e);
            }

            int useproduct = -1;

            if (process_mo != null) {
                String msisdn = process_mo.getMsisdn();
                String transaction_id = process_mo.getTransaction_id();
                String product_code = process_mo.getProductCode();
                String exchange_mode = process_mo.getExchangeMode();
                Timestamp receive_time = process_mo.getReceiveTime();
                String mo_his_desc = "";

                if (!StringUtils.isBlank(product_code)) {
                    Product product = SETPRODUCT.get(product_code);

                    // get day of registration in the week
                    List<String> listFrameTime = null;
                    if (!StringUtils.isBlank(product.getFrameTimeValidity())) {
                        Pattern ptn = Pattern.compile("\\-");
                        listFrameTime = Arrays.asList(ptn.split(product.getFrameTimeValidity().trim()));
                    }

                    Time startFrameTime = null;
                    Time endFrameTime = null;
                    if (listFrameTime != null && !listFrameTime.isEmpty()) {
                        startFrameTime = getTimeToString(listFrameTime.get(0));
                        endFrameTime = getTimeToString(listFrameTime.get(1));
                    }
                    // get live duration of offer
                    Date prod_start_date = product.getStartTime();
                    Date prod_end_date = product.getEndTime();

                    boolean isframeVal = product.getIsFrameValidity();

                    useproduct = 0;

                    if (useproduct == 0) {
                        if (!isframeVal) {
                            if (prod_start_date != null && prod_end_date != null) {
                                if (prod_start_date.after(prod_end_date)) {
                                    useproduct = 1;               // start time is after end time. wrong time configuration
                                    logger.warn("OFFER :" + product_code + " have START-TIME=" + prod_start_date + " which is after END-TIME =" + prod_end_date);
                                } else {
                                    if (prod_start_date.after(receive_time)) {
                                        useproduct = 2;            // start time is after receive time customer cannot register to product. product not available.
                                        logger.warn("OFFER :" + product_code + " have START-TIME=" + prod_start_date + " which is after CURRENT-TIME =" + receive_time);
                                    }
                                    if (prod_end_date.before(receive_time)) {
                                        useproduct = 3;            // end time is before receive time customer cannot register to product. product is expire
                                        logger.warn("OFFER :" + product_code + " have END-TIME=" + prod_end_date + " which is before CURRENT-TIME =" + receive_time);
                                    }
                                }
                            } else if (prod_start_date != null) {
                                if (prod_start_date.after(receive_time)) {
                                    useproduct = 2;            // start time is after receive time customer cannot register to product. product not available.
                                    logger.warn("OFFER :" + product_code + " have START-TIME=" + prod_start_date + " which is after CURRENT-TIME =" + receive_time);
                                }
                            } else if (prod_end_date != null) {
                                if (prod_end_date.before(receive_time)) {
                                    useproduct = 3;            // end time is before receive time customer cannot register to product. product is expire
                                    logger.warn("OFFER :" + product_code + " have END-TIME=" + prod_end_date + " which is before CURRENT-TIME =" + receive_time);

                                }
                            }
                        }
                    } else {

                        if (startFrameTime != null && endFrameTime != null) {
                            if (startFrameTime.after(endFrameTime)) {
                                useproduct = 1;               // start time is after end time. wrong time configuration
                                logger.warn("OFFER :" + product_code + " have START-TIME=" + startFrameTime + " which is after END-TIME =" + endFrameTime);
                            } else {
                                if (startFrameTime.after(receive_time)) {
                                    useproduct = 2;            // start time is after receive time customer cannot register to product. product not available.
                                    logger.warn("OFFER :" + product_code + " have START-TIME=" + startFrameTime + " which is after CURRENT-TIME =" + receive_time);
                                }
                                if (endFrameTime.before(receive_time)) {
                                    useproduct = 3;            // end time is before receive time customer cannot register to product. product is expire
                                    logger.warn("OFFER :" + product_code + " have END-TIME=" + endFrameTime + " which is before CURRENT-TIME =" + receive_time);
                                }
                            }
                        } else if (startFrameTime != null) {
                            if (startFrameTime.after(receive_time)) {
                                useproduct = 2;            // start time is after receive time customer cannot register to product. product not available.
                                logger.warn("OFFER :" + product_code + " have START-TIME=" + startFrameTime + " which is after CURRENT-TIME =" + receive_time);
                            }
                        } else if (endFrameTime != null) {
                            if (endFrameTime.before(receive_time)) {
                                useproduct = 3;            // end time is before receive time customer cannot register to product. product is expire
                                logger.warn("OFFER :" + product_code + " have END-TIME=" + endFrameTime + " which is before CURRENT-TIME =" + receive_time);

                            }
                        }
                    }

                   Register oldReg = registerRepo.findRegisterByMsisdnAndProduct(msisdn, product_code);

                    switch (useproduct) {
                        case 0:
                            if (oldReg == null) {
                                process_mo.setNotificationCode("DEL-PRODUCT-NOT-REGISTER-" + product_code);
                                mo_his_desc = "DEL-PRODUCT-NOT-REGISTER-" + product_code;
                            } else {
                                int status = oldReg.getStatus();
                                if (status == 1 || status == 2 ) {
                                    oldReg.setStatus(0);
                                    oldReg.setUnregTime(new Date());
  
                                    registerRepo.save(oldReg);
                                    process_mo.setNotificationCode("DEL-PRODUCT-SUCCESS-" + product_code);  // CUSTOMER go from ACTIVE or PENDING to CANCEL ON THAT PRODUCT
                                    mo_his_desc = "DEL-PRODUCT-SUCCESS-" + product_code;
                                    
                                }
                                if (status == 0 || status == -1) {
                                    process_mo.setNotificationCode("DEL-PRODUCT-ALREADY-CANCEL-" + product_code);  //CUSTOMER IS CANCEL ON THAT PRODUCT
                                    mo_his_desc = "DEL-PRODUCT-ALREADY-CANCEL-" + product_code;
                                }

                            }
                            break;
                        case 1:
                        case 2:
                        case 3:
                            process_mo.setNotificationCode("DEL-PRODUCT-WRONG-TIME-" + product_code);
                            mo_his_desc = "DEL-PRODUCT-WRONG-TIME-" + product_code;
                            break;
                    }

                } else {
                    process_mo.setNotificationCode("DEL-PRODUCT-NOT-EXIST");
                    mo_his_desc = "DEL-PRODUCT-NOT-EXIST";
                }

                // send to sender
                Sender.addMo_Queue(process_mo);

                 Timestamp last_time = new Timestamp(System.currentTimeMillis());
                long diffInMS = (last_time.getTime() - receive_time.getTime());

                Mo_Hist mo_hist = new Mo_Hist();

                mo_hist.setActionType(process_mo.getActionType());
                mo_hist.setChannel(process_mo.getSendChannel());
                mo_hist.setCommandCode(process_mo.getCommanCode());
                mo_hist.setCommandName(process_mo.getCommandName());
                mo_hist.setContent(process_mo.getContent());
                mo_hist.setMsisdn(msisdn);
                mo_hist.setDuration(diffInMS);
                mo_hist.setReceiveTime(process_mo.getReceiveTime());
                mo_hist.setTransactionId(transaction_id);
                mo_hist.setProcessUnit("Process_Check");
                mo_hist.setIpAddress(address.getHostName() + "@" + address.getHostAddress());
                mo_hist.setDescription(mo_his_desc);
                mo_hist.setExchangeMode(exchange_mode);
                mo_hist.setParamName(process_mo.getParamName());
                mo_hist.setServiceName(process_mo.getServiceName());

                mohisRepo.save(mo_hist);
                
                logger.info("insert into mo_his");
            }

            try {
                Thread.sleep(sleep_duration);
            } catch (Exception e) {
            }

        }

    }

    private Timestamp getExpire_Time(String validity, Timestamp current_time) {
        Timestamp result = null;

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(current_time.getTime());

        if (validity.toUpperCase().startsWith("D")) {
            String value = validity.replace("D", "");
            try {
                int nbDay = Integer.parseInt(value);
                cal.add(Calendar.DAY_OF_MONTH, nbDay);
                result = new Timestamp(cal.getTime().getTime());
            } catch (Exception e) {

            }
        } else if (validity.toUpperCase().startsWith("H")) {
            String value = validity.replace("H", "");
            try {
                int nbHour = Integer.parseInt(value);
                cal.add(Calendar.HOUR_OF_DAY, nbHour);
                result = new Timestamp(cal.getTime().getTime());
            } catch (Exception e) {

            }
        }
        return result;
    }

    private Time getTimeToString(String time_value) {

        Time time = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss"); //if 24 hour format
            java.util.Date d1 = (java.util.Date) format.parse(time_value);
            time = new java.sql.Time(d1.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;

    }

    public static void executeRunnables(final ExecutorService service, List<Runnable> runnables) {
        //On exécute chaque "Runnable" de la liste "runnables"
        for (Runnable r : runnables) {

            service.execute(r);
        }
        service.shutdown();
    }
}
