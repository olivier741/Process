/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.process.thread.register;

import com.tatsinktech.process.beans.Param;
import com.tatsinktech.process.beans.Process_Request;
import com.tatsinktech.process.beans.WS_Request;
import com.tatsinktech.process.services.BillingClient;
import com.tatsinktech.process.config.Load_Configuration;
import com.tatsinktech.process.model.register.Command;
import com.tatsinktech.process.model.register.Mo_Hist;
import com.tatsinktech.process.model.register.Product;
import com.tatsinktech.process.model.register.Promotion;
import com.tatsinktech.process.model.register.Reduction_Type;
import com.tatsinktech.process.model.register.Register;
import com.tatsinktech.process.model.repository.Mo_HistRepository;
import com.tatsinktech.process.model.repository.RegisterRepository;
import com.tatsinktech.process.services.CommunService;
import com.tatsinktech.process.thread.sender.Sender;
import com.tatsinktech.process.util.Utils;
import java.net.InetAddress;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
 * @author olivier
 */
@Component
public class Process_Register implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(Process_Register.class);

    private static int sleep_duration;
    private static InetAddress address;
    private static BlockingQueue<Process_Request> reg_queue;
    private static HashMap<String, Command> SETCOMMAND;
    private static HashMap<String, Product> SETPRODUCT;
    private HashMap<String, String> SETVARIABLE;

    @Autowired
    private RegisterRepository registerRepo;

    @Autowired
    private Mo_HistRepository mohisRepo;

    @Autowired
    private CommunService communsrv;

    @Autowired
    private Load_Configuration commonConfig;

    @Autowired
    private BillingClient billClient;

    public static void setReg_queue(BlockingQueue<Process_Request> reg_queue) {
        Process_Register.reg_queue = reg_queue;
    }

    public static void addMo_Queue(Process_Request process_req) {
        try {
            reg_queue.put(process_req);
//            logger.info("ADD message in the queue :"+ process_req);
        } catch (InterruptedException e) {
            logger.error("Error to add in reg_queue :" + process_req, e);
        }

    }

    @PostConstruct
    private void init() {
        Process_Register.sleep_duration = Integer.parseInt(commonConfig.getApplicationProcessRegSleepDuration());
        Process_Register.SETCOMMAND = commonConfig.getSETCOMMAND();
        Process_Register.SETPRODUCT = commonConfig.getSETPRODUCT();
        Process_Register.address = Utils.gethostName();

    }

    @Override
    public void run() {

        logger.info("################################## START PROCESS REGISTER ###########################");
        while (true) {
            // Removing an element from the Queue using poll()
            // The poll() method returns null if the Queue is empty.
            Process_Request process_mo = null;

            try {
                //consuming messages 
                process_mo = reg_queue.take();
                logger.info("Get message in Register queue :" + process_mo);
                logger.info("Register Queue size :" + reg_queue.size());
            } catch (InterruptedException e) {
                logger.error("Error to Get in reg_queue :" + process_mo, e);
            }
            int useproduct = -1;
            long charge_fee = 0;
            int charge_status = 0;
            Timestamp charge_time = null;

            if (process_mo != null) {
                String msisdn = process_mo.getMsisdn();
                String transaction_id = process_mo.getTransaction_id();
                String product_code = process_mo.getProductCode().trim().toUpperCase(); 
                String exchange_mode = process_mo.getExchangeMode().trim().toUpperCase();
                Timestamp receive_time = process_mo.getReceiveTime();
                String mo_his_desc = "";

                if (!StringUtils.isBlank(product_code)) {
                    Register oldReg = registerRepo.findRegisterByMsisdnAndProduct(msisdn, product_code);
                    Product product = SETPRODUCT.get(product_code);
                    charge_fee = product.getRegFee();
                    // get restric offer
                    List<String> listRestric_product = null;
                    if (!StringUtils.isBlank(product.getRestrictProduct())) {
                        Pattern ptn = Pattern.compile("\\|");
                        listRestric_product = Arrays.asList(ptn.split(product.getRestrictProduct().toUpperCase().trim()));
                    }

                    // get day of registration in the week
                    List<String> listRestrictDay = null;
                    if (!StringUtils.isBlank(product.getRestrictConstantValidity())) {
                        Pattern ptn = Pattern.compile("\\|");
                        listRestrictDay = Arrays.asList(ptn.split(product.getRestrictConstantValidity().trim()));
                    }

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

                    boolean isextend = false;
                    if (product.getIsExtend() != null) {
                        isextend = product.getIsExtend();
                    }
                    boolean isOveride = false;
                    if (product.getIsOverideReg() != null) {
                        isOveride = product.getIsOverideReg();
                    }

                    boolean isframeVal = false;
                    if (product.getIsFrameValidity() != null) {
                        isframeVal = product.getIsFrameValidity();
                    }

                    boolean isNotifyExt = false;
                    if (product.getIsNotifyExtend() != null) {
                        isNotifyExt = product.getIsNotifyExtend();
                    }

                    String validity = product.getValidity();

                    Calendar c = Calendar.getInstance();
                    c.setTime(receive_time);
                    String dayOfWeek = String.valueOf(c.get(Calendar.DAY_OF_WEEK));

                    Timestamp expire_time = getExpire_Time(validity, receive_time);

                    useproduct = 0;

                    // step 1
                    if (listRestric_product != null && !listRestric_product.isEmpty()) {
                        List<Register> listRegActive = registerRepo.findAllActiveRegisterByMsisdn(msisdn);
                        if (listRegActive != null && !listRegActive.isEmpty()) {
                            for (Register reg : listRegActive) {
                                if (listRestric_product.contains(reg.getProduct().getProductCode())) {
                                    useproduct = 1;   // if customer already have restrict offer he cannot register
                                    logger.warn("OFFER :" + product_code + " have register to restrict offer : " + reg.getProduct().getProductCode());
                                    break;
                                }
                            }
                        }
                    }

                    // step 2
                    if (useproduct == 0) {
                        if (!isframeVal) {
                            if (prod_start_date != null && prod_end_date != null) {
                                if (prod_start_date.after(prod_end_date)) {
                                    useproduct = 2;               // start time is after end time. wrong time configuration
                                    logger.warn("OFFER :" + product_code + " have START-TIME=" + prod_start_date + " which is after END-TIME =" + prod_end_date);
                                } else {
                                    if (prod_start_date.after(receive_time)) {
                                        useproduct = 3;            // start time is after receive time customer cannot register to product. product not available.
                                        logger.warn("OFFER :" + product_code + " have START-TIME=" + prod_start_date + " which is after CURRENT-TIME =" + receive_time);
                                    }
                                    if (prod_end_date.before(receive_time)) {
                                        useproduct = 4;            // end time is before receive time customer cannot register to product. product is expire
                                        logger.warn("OFFER :" + product_code + " have END-TIME=" + prod_end_date + " which is before CURRENT-TIME =" + receive_time);
                                    }
                                }
                            } else if (prod_start_date != null) {
                                if (prod_start_date.after(receive_time)) {
                                    useproduct = 3;            // start time is after receive time customer cannot register to product. product not available.
                                    logger.warn("OFFER :" + product_code + " have START-TIME=" + prod_start_date + " which is after CURRENT-TIME =" + receive_time);
                                }
                            } else if (prod_end_date != null) {
                                if (prod_end_date.before(receive_time)) {
                                    useproduct = 4;            // end time is before receive time customer cannot register to product. product is expire
                                    logger.warn("OFFER :" + product_code + " have END-TIME=" + prod_end_date + " which is before CURRENT-TIME =" + receive_time);

                                }
                            }
                        }
                    } else {

                        if (startFrameTime != null && endFrameTime != null) {
                            if (startFrameTime.after(endFrameTime)) {
                                useproduct = 2;               // start time is after end time. wrong time configuration
                                logger.warn("OFFER :" + product_code + " have START-TIME=" + startFrameTime + " which is after END-TIME =" + endFrameTime);
                            } else {
                                if (startFrameTime.after(receive_time)) {
                                    useproduct = 3;            // start time is after receive time customer cannot register to product. product not available.
                                    logger.warn("OFFER :" + product_code + " have START-TIME=" + startFrameTime + " which is after CURRENT-TIME =" + receive_time);
                                }
                                if (endFrameTime.before(receive_time)) {
                                    useproduct = 4;            // end time is before receive time customer cannot register to product. product is expire
                                    logger.warn("OFFER :" + product_code + " have END-TIME=" + endFrameTime + " which is before CURRENT-TIME =" + receive_time);
                                }
                            }
                        } else if (startFrameTime != null) {
                            if (startFrameTime.after(receive_time)) {
                                useproduct = 3;            // start time is after receive time customer cannot register to product. product not available.
                                logger.warn("OFFER :" + product_code + " have START-TIME=" + startFrameTime + " which is after CURRENT-TIME =" + receive_time);
                            }
                        } else if (endFrameTime != null) {
                            if (endFrameTime.before(receive_time)) {
                                useproduct = 4;            // end time is before receive time customer cannot register to product. product is expire
                                logger.warn("OFFER :" + product_code + " have END-TIME=" + endFrameTime + " which is before CURRENT-TIME =" + receive_time);

                            }
                        }
                    }

                    // step 3
                    if (useproduct == 0) {
                        if (listRestrictDay != null && !listRestrictDay.isEmpty() && listRestrictDay.contains(dayOfWeek)) {
                            useproduct = 5;                   // customer cannot register to this offer on that day
                            logger.warn("OFFER :" + product_code + " have DAY-OF-WEEK=" + dayOfWeek + " as restriction Day");

                        }
                    }

                    // step 4
                    if (useproduct == 0) {
                        if (!isOveride) {
                            if (oldReg != null) {
                                logger.warn("OFFER :" + product_code + " cannot be overring");
                                useproduct = 6;
                            }
                        }
                    }
                    // step 5
                    if (useproduct == 0) {

                        Promotion promo = product.getPromotion();
                        useproduct = executePromotion(msisdn, transaction_id, product, receive_time, promo);
                        if (useproduct == 0) {
                            useproduct = executeProductWithoutPromotion(msisdn, transaction_id, product);
                        }

                    }

                    Register reg = null;

                    switch (useproduct) {
                        case 0:
                            if (isOveride) {

                                if (oldReg != null) {   // override old register
                                    reg = oldReg;
                                    reg.setNumberReg(oldReg.getNumberReg() + 1);
                                    reg.setRenewTime(new Date());
                                    reg.setUnregTime(null);
                                    process_mo.setNotificationCode("REG-PRODUCT-SUCCESS-OVERIDE-" + product_code);
                                    mo_his_desc = "REG-PRODUCT-SUCCESS-OVERIDE-" + product_code;
                                } else {                // not yet register
                                    reg = new Register();
                                    reg.setNumberReg(1);
                                    reg.setRegTime(new Date());
                                    process_mo.setNotificationCode("REG-PRODUCT-SUCCESS-" + product_code);
                                    mo_his_desc = "REG-PRODUCT-SUCCESS-" + product_code;
                                }

                            } else {
                                if (oldReg == null) { // not override and don't have old registration
                                    reg = new Register();
                                    reg.setNumberReg(1);
                                    reg.setRegTime(new Date());
                                    process_mo.setNotificationCode("REG-PRODUCT-SUCCESS-" + product_code);
                                    mo_his_desc = "REG-PRODUCT-SUCCESS-" + product_code;
                                }
                            }

                            if (reg != null) {
                                reg.setAutoextend(isextend);
                                reg.setExpireTime(expire_time);
                                reg.setMsisdn(msisdn);
                                reg.setProduct(product);
                                reg.setProductCode(product_code);
                                reg.setStatus(1);
                                reg.setTransactionId(transaction_id);
                            }
                            charge_status = 0;

                            registerRepo.save(reg);
                            break;
                        case 1:
                            process_mo.setNotificationCode("REG-PRODUCT-RESTRICTION-EXIST-" + product_code);
                            mo_his_desc = "REG-PRODUCT-RESTRICTION-EXIST-" + product_code;
                            charge_status = 1;
                            break;
                        case 2:
                        case 3:
                        case 4:
                            process_mo.setNotificationCode("REG-PRODUCT-WRONG-TIME-" + product_code);
                            mo_his_desc = "REG-PRODUCT-WRONG-TIME-" + product_code;
                            charge_status = 2;
                            break;
                        case 5:
                            process_mo.setNotificationCode("REG-PRODUCT-INVALID-DAY-" + product_code);
                            mo_his_desc = "REG-PRODUCT-INVALID-DAY-" + product_code;
                            charge_status = 3;
                            break;
                        case 6:
                            process_mo.setNotificationCode("REG-PRODUCT-NOT-OVERIDE-" + product_code);
                            mo_his_desc = "REG-PRODUCT-NOT-OVERIDE-" + product_code;
                            charge_status = 4;
                            break;
                        case 7:
                            process_mo.setNotificationCode("REG-PRODUCT-NOT-MONEY-" + product_code);
                            mo_his_desc = "REG-PRODUCT-NOT-MONEY-" + product_code;
                            charge_status = 6;
                            break;
                        case 8:
                            if (oldReg != null) {
                                oldReg.setStatus(-1);
                                oldReg.setCancelTime(new Date());
                                registerRepo.save(oldReg);

                            }
                            process_mo.setNotificationCode("REG-PRODUCT-CUSTOMER-BLOCK-" + product_code);
                            mo_his_desc = "REG-PRODUCT-CUSTOMER-BLOCK-" + product_code;
                            charge_status = 7;
                            break;
                        case 9:
                            process_mo.setNotificationCode("REG-PRODUCT-WRONG-API-CONNECTION-" + product_code);
                            mo_his_desc = "REG-PRODUCT-WRONG-API-CONNECTION-" + product_code;
                            charge_status = 8;
                            break;

                    }

                } else {
                    process_mo.setNotificationCode("REG-PRODUCT-NOT-EXIST");
                    mo_his_desc = "PRODUCT NOT EXIST";
                    charge_status = -1;
                }

                // send to sender
                Sender.addMo_Queue(process_mo);

                Timestamp last_time = new Timestamp(System.currentTimeMillis());
                long diffInMS = (last_time.getTime() - receive_time.getTime());

                Mo_Hist mo_hist = new Mo_Hist();

                mo_hist.setActionType(process_mo.getActionType());
                mo_hist.setChannel(process_mo.getSendChannel().trim().toUpperCase());
                mo_hist.setCommandCode(process_mo.getCommanCode().trim().toUpperCase());
                mo_hist.setCommandName(process_mo.getCommandName().trim().toUpperCase());
                mo_hist.setContent(process_mo.getContent());
                mo_hist.setMsisdn(msisdn);
                mo_hist.setDuration(diffInMS);
                mo_hist.setChargeStatus(charge_status);
                if (useproduct == 0) {
                    mo_hist.setChargeFee(charge_fee);
                    mo_hist.setChargeTime(charge_time);
                }
                mo_hist.setReceiveTime(process_mo.getReceiveTime());
                mo_hist.setTransactionId(transaction_id);
                mo_hist.setProcessUnit("Process_Reg");
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

    private int executePromotion(String msisdn, String transaction_id, Product product, Date receive_time, Promotion promo) {
        long charge_fee = 0;
        int useproduct = 0;
        int result = 0;
        if (promo != null && communsrv.authorizationPromo(msisdn.trim(), promo)) {  // offer have promotion and allow to get promotion
            String promoName = promo.getPromotionName();
            String product_code = product.getProductCode();
            Date promo_start_time = promo.getStartTime();
            Date promo_end_time = promo.getEndTime();

            if (promo_start_time != null && promo_end_time != null) {
                if (promo_start_time.after(promo_end_time)) {
                    // start time is after end time wrong time configuration     
                    useproduct = 1;
                    logger.warn("OFFER :" + product_code + " with PROMOTION =" + promoName + " will not be take care");
                    logger.warn("OFFER :" + product_code + " with PROMOTION =" + promoName + " have PROMO-START-TIME =" + promo_start_time + " which is after PROMO-END-TIME =" + promo_end_time);

                } else {
                    if (promo_start_time.after(receive_time)) {
                        // start time is after receive time customer cannot register to promotion. promotion not available.
                        logger.warn("OFFER :" + product_code + " with PROMOTION =" + promoName + " will not be take care");
                        logger.warn("OFFER :" + product_code + " with PROMOTION =" + promoName + " have PROMO-START-TIME =" + promo_start_time + " which is after CURRENT-TIME =" + receive_time);
                        useproduct = 2;
                    }
                    if (promo_end_time.before(receive_time)) {
                        logger.warn("OFFER :" + product_code + " with PROMOTION =" + promoName + " will not be take care");
                        logger.warn("OFFER :" + product_code + " with PROMOTION =" + promoName + " have PROMO-END-TIME =" + promo_end_time + " which is before CURRENT-TIME =" + receive_time);
                        useproduct = 3;            // end time is before receive time customer cannot register to promotion. promotion is expire
                    }
                }
            } else if (promo_start_time != null) {
                if (promo_start_time.after(receive_time)) {
                    // start time is after receive time customer cannot register to promotion. promotion not available.
                    logger.warn("OFFER :" + product_code + " with PROMOTION =" + promoName + " will not be take care");
                    logger.warn("OFFER :" + product_code + " with PROMOTION =" + promoName + " have PROMO-START-TIME =" + promo_start_time + " which is after CURRENT-TIME =" + receive_time);
                    useproduct = 4;
                }
            } else if (promo_end_time != null) {
                if (promo_end_time.before(receive_time)) {
                    // end time is before receive time customer cannot register to promotion. promotion is expire
                    logger.warn("OFFER :" + product_code + " with PROMOTION =" + promoName + " will not be take care");
                    logger.warn("OFFER :" + product_code + " with PROMOTION =" + promoName + " have PROMO-END-TIME =" + promo_end_time + " which is before CURRENT-TIME =" + receive_time);
                    useproduct = 5;
                }
            }

            // step 6
            if (useproduct == 0) {

                if (product.getRegFee() > 0 || promo.getPromotionRegFee() > 0) {
                    charge_fee = product.getRegFee();
                    long reductPerc = 0;
                    long prod_fee = product.getRegFee();
                    long reduct_val = 0;
                    Reduction_Type reductMode = promo.getReductionMode();
                    switch (reductMode) {
                        case PERCENTAGE:
                            reductPerc = promo.getPercentageReg();
                            reduct_val = prod_fee * reductPerc / 100;
                            charge_fee = Math.abs(prod_fee - reduct_val);

                            break;
                        case VALUE:
                            reduct_val = promo.getPromotionRegFee();
                            charge_fee = Math.abs(prod_fee - reduct_val);
                            break;
                    }

                    List<Param> listParam = new ArrayList<Param>();
                    listParam.add(new Param(commonConfig.getChargingAliasAmount(), String.valueOf(charge_fee)));
                    listParam.add(new Param(commonConfig.getChargingAliasMsisdn(), msisdn.trim()));
                    listParam.add(new Param(commonConfig.getChargingAliasProduct(), product_code));
                    listParam.add(new Param(commonConfig.getChargingAliasTransaction(), transaction_id));
                    listParam.add(new Param(commonConfig.getChargingAliasDescription(), "Charge " + product_code + " for " + charge_fee));

                    WS_Request wsRequest = new WS_Request();
                    wsRequest.setAmount(charge_fee);
                    wsRequest.setCharge_reason("Charge " + product_code + " for " + charge_fee);
                    wsRequest.setMsisdn(msisdn);
                    wsRequest.setProcessUnit("Process_Reg");
                    wsRequest.setTransactionId(transaction_id);
                    wsRequest.setRequest_time(new Date());
                    wsRequest.setWsClientlogin(commonConfig.getChargingClientName());
                    wsRequest.setWsClientpassword(commonConfig.getChargingPassword());
                    wsRequest.setWs_AccessMgntName(commonConfig.getChargingWsManagement());
                    wsRequest.setProductCode(product_code);
                    wsRequest.setWSparam(listParam);

                    int resp = billClient.charge(wsRequest);

                    if (resp == 1) {  // not enough money
                        logger.warn("OFFER :" + product_code + " with PROMOTION =" + promoName + " MSISDN = " + msisdn + " don't have enough money");
                        result = 7;
                    }

                    if (resp == 2) {  // customer is block or inactive or cancel
                        logger.warn("OFFER :" + product_code + " with PROMOTION =" + promoName + " MSISDN = " + msisdn + " is inactive or Block or Cancel");
                        result = 8;
                    }

                    if (resp == -1) {  // webservice error
                        logger.warn("OFFER :" + product_code + " with PROMOTION =" + promoName + " MSISDN = " + msisdn + " WEBSERVICE FAIL or NOT PROCESS REQUEST");
                        result = 9;
                    }
                }
            }
        }
        return result;
    }

    private int executeProductWithoutPromotion(String msisdn, String transaction_id, Product product) {
        int result = 0;
        long charge_fee = 0;
        String product_code = product.getProductCode();

        if (product.getRegFee() > 0) {
            charge_fee = product.getRegFee();

            List<Param> listParam = new ArrayList<Param>();
            listParam.add(new Param(commonConfig.getChargingAliasAmount(), String.valueOf(charge_fee)));
            listParam.add(new Param(commonConfig.getChargingAliasMsisdn(), msisdn.trim()));
            listParam.add(new Param(commonConfig.getChargingAliasProduct(), product_code));
            listParam.add(new Param(commonConfig.getChargingAliasTransaction(), transaction_id));
            listParam.add(new Param(commonConfig.getChargingAliasDescription(), "Charge " + product_code + " for " + charge_fee));

            WS_Request wsRequest = new WS_Request();
            wsRequest.setAmount(charge_fee);
            wsRequest.setCharge_reason("Charge " + product_code + " for " + charge_fee);
            wsRequest.setMsisdn(msisdn);
            wsRequest.setProcessUnit("Process_Reg");
            wsRequest.setTransactionId(transaction_id);
            wsRequest.setRequest_time(new Date());
            wsRequest.setWsClientlogin(commonConfig.getChargingClientName());
            wsRequest.setWsClientpassword(commonConfig.getChargingPassword());
            wsRequest.setWs_AccessMgntName(commonConfig.getChargingWsManagement());
            wsRequest.setProductCode(product_code);
            wsRequest.setWSparam(listParam);

            int resp = billClient.charge(wsRequest);

            if (resp == 1) {  // not enough money
                logger.warn("OFFER :" + product_code + " MSISDN = " + msisdn + " don't have enough money");
                result = 7;
            }

            if (resp == 2) {  // customer is block or inactive or cancel
                logger.warn("OFFER :" + product_code + " MSISDN = " + msisdn + " is inactive or Block or Cancel");

                result = 8;
            }
            

            if (resp == -1) {  // webservice error
                logger.warn("OFFER :" + product_code + " MSISDN = " + msisdn + " WEBSERVICE FAIL or NOT PROCESS REQUEST");
                result = 9;
            }
        }
        return result;
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
