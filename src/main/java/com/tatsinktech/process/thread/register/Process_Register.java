/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.process.thread.register;

import com.tatsinktech.process.beans.Process_Request;
import com.tatsinktech.process.beans.WS_Request;
import com.tatsinktech.process.beans.WS_Response;
import com.tatsinktech.process.config.Load_Configuration;
import com.tatsinktech.process.model.register.Command;
import com.tatsinktech.process.model.register.Mo_Hist;
import com.tatsinktech.process.model.register.Product;
import com.tatsinktech.process.model.register.Promotion;
import com.tatsinktech.process.model.register.Reduction_Type;
import com.tatsinktech.process.model.register.Register;
import com.tatsinktech.process.model.repository.RegisterRepository;
import com.tatsinktech.process.services.CommunService;
import com.tatsinktechnologic.dao_repository.AliasJpaController;
import com.tatsinktechnologic.dao_repository.Mo_HistJpaController;
import com.tatsinktechnologic.dao_repository.RegisterJpaController;
import com.tatsinktechnologic.resfull.bean.WS_Block_Response;
import com.tatsinktechnologic.resfull.client.Webservice_Charge;
import com.tatsinktech.process.thread.sender.Sender;
import com.tatsinktech.process.util.Generators;
import com.tatsinktech.process.util.Utils;
import com.tatsinktechnologic.beans_entity.Alias;
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
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

/**
 *
 * @author olivier
 */
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
    private CommunService communsrv;

    @Autowired
    private static Load_Configuration commonConfig;

    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;

    public static void addMo_Queue(Process_Request process_req) {
        try {
            reg_queue.put(process_req);
//            logger.info("ADD message in the queue :"+ process_req);
        } catch (InterruptedException e) {
            logger.error("Error to add in reg_queue :" + process_req, e);
        }

    }

    public static void loadFeatures(BlockingQueue<Process_Request> reg_queue) {
        Process_Register.reg_queue = reg_queue;
        Process_Register.sleep_duration = Integer.parseInt(commonConfig.getApplicationProcessRegSleepDuration());
        Process_Register.SETCOMMAND = commonConfig.getSETCOMMAND();
        Process_Register.SETPRODUCT = commonConfig.getSETPRODUCT();
        Process_Register.address = Utils.gethostName();

    }

    public Process_Register() {
        SETVARIABLE = new HashMap<String, String>();
        SETVARIABLE.put("_reg_date_", null);
        SETVARIABLE.put("_exp_date_", null);
        SETVARIABLE.put("_reg_duration_day", null);
        SETVARIABLE.put("_reg_duration_hour", null);
        SETVARIABLE.put("_reg_fee_", null);
        SETVARIABLE.put("_alias_", null);
        SETVARIABLE.put("_chat_group_", null);
        SETVARIABLE.put("_offer_", null);
        SETVARIABLE.put("_list_offer_", null);
        SETVARIABLE.put("_list_offer_reg_date_", null);
        SETVARIABLE.put("_list_offer_exp_date_", null);
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

            String api_error = "";
            String api_desc_error = "";
            int useproduct = -1;
            long charge_fee = 0;
            int charge_status = 0;
            Timestamp charge_time = null;

            if (process_mo != null) {
                String msisdn = process_mo.getMsisdn();
                String transaction_id = process_mo.getTransaction_id();
                String product_code = process_mo.getProductCode();
                String exchange_mode = process_mo.getExchangeMode();
                Timestamp receive_time = process_mo.getReceiveTime();
                String mo_his_desc = "";

                if (!StringUtils.isBlank(product_code)) {
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

                    boolean isextend = product.isIsExtend();
                    boolean isOveride = product.isIsOverideReg();
                    boolean isframeVal = product.isIsFrameValidity();
                    boolean isNotifyExt = product.isIsNotifyExtend();

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

                        Promotion promo = product.getPromotion();

                        if (promo != null) {  // offer have promotion

                            Date promo_start_time = promo.getStartTime();
                            Date promo_end_time = promo.getEndTime();

                            if (promo_start_time != null && promo_end_time != null) {
                                if (promo_start_time.after(promo_end_time)) {
                                    // start time is after end time wrong time configuration     
                                    useproduct = 6;
                                    logger.warn("OFFER :" + product_code + " with PROMOTION =" + promo + " will not be take care");
                                    logger.warn("OFFER :" + product_code + " with PROMOTION =" + promo + " have PROMO-START-TIME =" + promo_start_time + " which is after PROMO-END-TIME =" + promo_end_time);

                                } else {
                                    if (promo_start_time.after(receive_time)) {
                                        // start time is after receive time customer cannot register to promotion. promotion not available.
                                        logger.warn("OFFER :" + product_code + " with PROMOTION =" + promo + " will not be take care");
                                        logger.warn("OFFER :" + product_code + " with PROMOTION =" + promo + " have PROMO-START-TIME =" + promo_start_time + " which is after CURRENT-TIME =" + receive_time);
                                        useproduct = 7;
                                    }
                                    if (promo_end_time.before(receive_time)) {
                                        logger.warn("OFFER :" + product_code + " with PROMOTION =" + promo + " will not be take care");
                                        logger.warn("OFFER :" + product_code + " with PROMOTION =" + promo + " have PROMO-END-TIME =" + promo_end_time + " which is before CURRENT-TIME =" + receive_time);
                                        useproduct = 8;            // end time is before receive time customer cannot register to promotion. promotion is expire
                                    }
                                }
                            } else if (promo_start_time != null) {
                                if (promo_start_time.after(receive_time)) {
                                    // start time is after receive time customer cannot register to promotion. promotion not available.
                                    logger.warn("OFFER :" + product_code + " with PROMOTION =" + promo + " will not be take care");
                                    logger.warn("OFFER :" + product_code + " with PROMOTION =" + promo + " have PROMO-START-TIME =" + promo_start_time + " which is after CURRENT-TIME =" + receive_time);
                                    useproduct = 7;
                                }
                            } else if (promo_end_time != null) {
                                if (promo_end_time.before(receive_time)) {
                                    // end time is before receive time customer cannot register to promotion. promotion is expire
                                    logger.warn("OFFER :" + product_code + " with PROMOTION =" + promo + " will not be take care");
                                    logger.warn("OFFER :" + product_code + " with PROMOTION =" + promo + " have PROMO-END-TIME =" + promo_end_time + " which is before CURRENT-TIME =" + receive_time);
                                    useproduct = 8;
                                }
                            }

                            if (useproduct == 0) {
                                if (!communsrv.authorizationPromo(msisdn.trim(), promo)) {
                                    useproduct = 9;         // customer cannot register to promotion. its phone number not obey to promotion restriction policy 
                                } else {

                                    if (product.getRegFee() > 0 || promo.getPromotionRegFee() > 0) {
                                        long charge_val = product.getRegFee();
                                        long reductPerc = 0;
                                        long prod_fee = product.getRegFee();
                                        long reduct_val = 0;
                                        Reduction_Type reductMode = promo.getReductionMode();
                                        switch (reductMode) {
                                            case PERCENTAGE:
                                                reductPerc = promo.getPercentageReg();
                                                reduct_val = prod_fee * reductPerc / 100;
                                                charge_val = Math.abs(prod_fee - reduct_val);

                                                break;
                                            case VALUE:
                                                reduct_val = promo.getPromotionRegFee();
                                                charge_val = Math.abs(prod_fee - reduct_val);
                                                break;
                                        }

                                        WS_Request wsRequest = new WS_Request();
                                        wsRequest.setAmount(charge_val);
                                        wsRequest.setCharge_reason("charge TOTO Month");
                                        wsRequest.setMsisdn(msisdn);
                                        wsRequest.setProcessUnit("Process_Reg");
                                        wsRequest.setTransactionId(transaction_id);
                                        wsRequest.setWs_AccessMgntName("accessMgt_ID");

                                        ResponseEntity<WS_Response> response = oAuth2RestTemplate.postForEntity("http://localhost:9090/api_gateway/chargeRequest", wsRequest, WS_Response.class);
                                        
                                        System.out.println("response status = " + response.getStatusCode());
                                        System.out.println("response status value = " + response.getStatusCodeValue());
                                        System.out.println("response Body = " + response.getBody());

                                        Webservice_Charge web_service = new Webservice_Charge();
                                        try {
                                            WS_Block_Response ws_resp = web_service.requestCharge_Product(msisdn, transaction_id, product);

                                            if (ws_resp != null) {
                                                charge_fee = ws_resp.getFee_charge();
                                                api_error = ws_resp.getListws_response().get(0).getAPI_GW_Error();
                                                api_desc_error = ws_resp.getListws_response().get(0).getAPI_GW_Description();
                                            } else {
                                                logger.error("API-GATEWAY Authentication issue ");
                                            }

                                        } catch (Exception e) {
                                            useproduct = 16;
                                            logger.error("API error : ", e);
                                        }
                                    }

                                    if (useproduct == 0) {
                                        if (!api_error.equals("00") && !api_error.equals("")) {
                                            if (api_error.equals("66")) {  // customer is block
                                                useproduct = 15;
                                            } else if (api_error.equals("33")) { // not enough money
                                                useproduct = 17;
                                            } else {
                                                useproduct = 13;
                                            }
                                        } else {
                                            if (expire_time == null) {
                                                useproduct = 14;
                                            }
                                        }
                                    }

                                }
                            } else {
                                if (product.getReg_fee() > 0) {
                                    Webservice_Charge web_service = new Webservice_Charge();
                                    try {
                                        WS_Block_Response ws_resp = web_service.requestCharge_Product(msisdn, transaction_id, product);

                                        if (ws_resp != null) {
                                            charge_fee = ws_resp.getFee_charge();
                                            api_error = ws_resp.getListws_response().get(0).getAPI_GW_Error();
                                            api_desc_error = ws_resp.getListws_response().get(0).getAPI_GW_Description();
                                        } else {
                                            logger.error("API-GATEWAY Authentication issue ");
                                        }

                                    } catch (Exception e) {
                                        useproduct = 16;
                                        logger.error("API error : ", e);
                                    }

                                }

                                if (useproduct == 0) {
                                    if (!api_error.equals("00") && !api_error.equals("")) {
                                        if (api_error.equals("66")) {  // customer is block
                                            useproduct = 15;
                                        } else if (api_error.equals("33")) { // not enough money
                                            useproduct = 17;
                                        } else {
                                            useproduct = 13;
                                        }
                                    } else {
                                        if (expire_time == null) {
                                            useproduct = 14;
                                        }
                                    }
                                }

                            }
                        } else {  // offer don't have promotion
                            if (product.getReg_fee() > 0) {
                                Webservice_Charge web_service = new Webservice_Charge();
                                try {
                                    WS_Block_Response ws_resp = web_service.requestCharge_Product(msisdn, transaction_id, product);

                                    if (ws_resp != null) {
                                        charge_fee = ws_resp.getFee_charge();
                                        api_error = ws_resp.getListws_response().get(0).getAPI_GW_Error();
                                        api_desc_error = ws_resp.getListws_response().get(0).getAPI_GW_Description();
                                    } else {
                                        logger.error("API-GATEWAY Authentication issue ");
                                    }

                                } catch (Exception e) {
                                    useproduct = 16;
                                    logger.error("API error : ", e);
                                }

                            }

                            if (useproduct == 0) {
                                if (!api_error.equals("00") && !api_error.equals("")) {
                                    if (api_error.equals("66")) {  // customer is block
                                        useproduct = 15;
                                    } else if (api_error.equals("33")) { // not enough money
                                        useproduct = 17;
                                    } else {
                                        useproduct = 13;
                                    }
                                } else {
                                    if (expire_time == null) {
                                        useproduct = 14;
                                    }
                                }
                            }

                        }

                    }

                    Register reg = null;
                    RegisterJpaController regCont = null;

                    switch (useproduct) {
                        case 0:
                            reg = communRepo.getRegister(msisdn, product);
                            boolean isNew = false;
                            if (reg == null) {
                                reg = new Register();
                                reg.setNumber_reg(1);
                                reg.setProduct(product);
                                reg.setReg_time(receive_time);
                                isNew = true;
                            } else {

                                int numberReg = reg.getNumber_reg();
                                reg.setNumber_reg(numberReg + 1);
                            }
                            reg.setAutoextend(isextend);
                            reg.setExpire_time(expire_time);
                            reg.setRenew_time(receive_time);
                            reg.setMsisdn(msisdn);
                            reg.setStatus(1);
                            reg.setTransaction_id(transaction_id);
                            reg.setExchange_mode(exchange_mode);

                            String regDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(receive_time);
                            String expDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(expire_time);

                            SETVARIABLE.put("_reg_date_", regDate);
                            SETVARIABLE.put("_exp_date_", expDate);

                            if (validity.toUpperCase().startsWith("D")) {
                                String value = validity.replace("D", "");
                                SETVARIABLE.put("_reg_duration_day", value);
                            } else if (validity.toUpperCase().startsWith("H")) {
                                String value = validity.replace("H", "");
                                SETVARIABLE.put("_reg_duration_hour", value);
                            }

                            SETVARIABLE.put("_reg_fee_", String.valueOf(charge_fee));

                            regCont = new RegisterJpaController(emf);

                            boolean success_reg = false;
                            if (isNew) {
                                regCont.create(reg);
                                process_mo.setNotification_code("REG-PRODUCT-SUCCESS-" + product_code);
                                mo_his_desc = api_desc_error;
                                success_reg = true;
                            } else {
                                if (isOveride) {
                                    try {
                                        regCont.edit(reg);
                                        process_mo.setNotification_code("REG-PRODUCT-SUCCESS-OVERIDE-" + product_code);
                                        mo_his_desc = api_desc_error;
                                        success_reg = true;
                                    } catch (Exception e) {
                                        logger.error("Customer don't have account of this Offer. Cannot Edit", e);
                                    }
                                } else {
                                    process_mo.setNotification_code("REG-PRODUCT-NOT-OVERIDE-" + product_code);
                                    mo_his_desc = "CUSTOMER ALREADY REGISTER TO THIS OFFER: CANNOT OVERIDE";
                                }

                            }

                            String alias_value = Generators.generateRandomDigit(3);

                            Alias alias = communRepo.getAlias(msisdn);

                            if (success_reg) {

                                if (alias == null) {
                                    alias = new Alias();
                                    alias.setAlias(alias_value);
                                    alias.setMsisdn(msisdn);

                                    AliasJpaController aliasController = new AliasJpaController(emf);
                                    aliasController.create(alias);
                                }
                            }

                            SETVARIABLE.put("_alias_", alias.getAlias());
                            SETVARIABLE.put("_offer_", reg.getProduct().getProduct_code());
                            process_mo.setSetvariable(SETVARIABLE);
                            charge_status = 0;
                            break;
                        case 1:
                            process_mo.setNotification_code("REG-PRODUCT-WRONG-TIME-" + product_code);
                            mo_his_desc = "WRONG TIME CONFIGURATION OF THIS PRODUCT";
                            charge_status = 1;
                            break;
                        case 2:
                            process_mo.setNotification_code("REG-PRODUCT-NOT-AVAILABLE-" + product_code);
                            mo_his_desc = "PRODUCT IS NOT AVAILABLE";
                            charge_status = 2;
                            break;
                        case 3:
                            process_mo.setNotification_code("REG-PRODUCT-EXPIRE-" + product_code);
                            mo_his_desc = "PRODUCT IS EXPIRE";
                            charge_status = 3;
                            break;
                        case 4:
                            process_mo.setNotification_code("REG-PRODUCT-INVALID-DAY-" + product_code);
                            mo_his_desc = "PRODUCT IS NOT VALID THIS DAY";
                            charge_status = 4;
                            break;
                        case 5:
                            process_mo.setNotification_code("REG-PRODUCT-WRONG-HOUR-" + product_code);
                            mo_his_desc = "WRONG HOUR CONFIGURATION OF THIS PRODUCT";
                            charge_status = 5;
                            break;
                        case 6:
                            process_mo.setNotification_code("REG-PRODUCT-NOT-AVAILABLE-HOUR-" + product_code);
                            mo_his_desc = "PRODUCT IS NOT AVAILABLE THIS HOUR";
                            charge_status = 6;
                            break;
                        case 7:
                            process_mo.setNotification_code("REG-PRODUCT-EXPIRE-HOUR-" + product_code);
                            mo_his_desc = "PRODUCT IS EXPIRE AT THIS HOUR";
                            charge_status = 7;
                            break;
                        case 8:
                            process_mo.setNotification_code("REG-PRODUCT-RESTRICTION-EXIST-" + product_code);
                            mo_his_desc = "CUSTOMER HAS REGISTER TO RESTRICT PRODUCT";
                            charge_status = 8;
                            break;
                        case 12:
                            process_mo.setNotification_code("REG-PRODUCT-PROMO-NOT-ALLOW-" + product_code);
                            mo_his_desc = "CUSTOMER NOT ALLOW TO GET PROMOTION";
                            charge_status = 12;
                            break;
                        case 13:
                            process_mo.setNotification_code("REG-PRODUCT-WRONG-CHARGE-" + product_code);
                            mo_his_desc = "CUSTOMER NOT CHARGE";
                            charge_status = 13;
                            break;
                        case 14:
                            process_mo.setNotification_code("REG-PRODUCT-WRONG-EXPIRRE_DATE-" + product_code);
                            mo_his_desc = "SYSTEM ERROR : CANNOT GET EXPIRE TIME OF PROMOTION";
                            charge_status = 14;
                            break;
                        case 15:
                            reg = communRepo.getRegister(msisdn, product);
                            if (reg != null) {
                                reg.setStatus(-1);
                                reg.setCancel_time(receive_time);
                                regCont = new RegisterJpaController(emf);
                                try {
                                    regCont.edit(reg);
                                } catch (Exception e) {
                                    logger.error("Customer don't have account of this Offer. Cannot Edit", e);
                                }
                            }
                            process_mo.setNotification_code("REG-PRODUCT-CUSTOMER-BLOCK-" + product_code);
                            mo_his_desc = "CUSTOMER IS BLOCK IN NETWORK | " + api_desc_error;
                            charge_status = 15;
                            break;
                        case 16:
                            process_mo.setNotification_code("REG-PRODUCT-WRONG-API-CONNECTION-" + product_code);
                            mo_his_desc = "Charging API Connection refused";
                            charge_status = 16;
                            break;
                        case 17:
                            process_mo.setNotification_code("REG-PRODUCT-NOT-MONEY-" + product_code);
                            mo_his_desc = "CUSTOMER NOT ENOUGH MONEY";
                            charge_status = 16;
                            break;
                    }

                } else {
                    process_mo.setNotification_code("REG-PRODUCT-NOT-EXIST");
                    mo_his_desc = "PRODUCT NOT EXIST";
                    charge_status = -1;
                }

                // send to sender
                Sender.addMo_Queue(process_mo);

                Timestamp last_time = new Timestamp(System.currentTimeMillis());
                long diffInMS = (last_time.getTime() - receive_time.getTime());

                Command cmd = SETCOMMAND.get(process_mo.getCommand_code());
                Mo_Hist mo_hist = new Mo_Hist();
                mo_hist.setAction_type(process_mo.getAction_type());
                mo_hist.setChannel(process_mo.getChannel());
                mo_hist.setContent(process_mo.getContent());
                mo_hist.setMsisdn(msisdn);
                mo_hist.setDuration(diffInMS);
                mo_hist.setCharge_status(charge_status);
                if (useproduct == 0) {
                    mo_hist.setCharge_fee(charge_fee);
                    mo_hist.setCharge_error(api_error + "|" + api_desc_error);
                    mo_hist.setCharge_time(charge_time);
                }
                mo_hist.setReceive_time(process_mo.getReceive_time());
                mo_hist.setCommand(cmd);
                mo_hist.setTransaction_id(transaction_id);
                mo_hist.setProcess_unit("Process_Reg");
                mo_hist.setIP_unit(address.getHostName() + "@" + address.getHostAddress());
                mo_hist.setError_description(mo_his_desc);

                Mo_HistJpaController mo_histController = new Mo_HistJpaController(emf);
                mo_histController.create(mo_hist);
                mo_hist.setExchange_mode(exchange_mode);

                logger.info("insert into mo_his");

            }

            try {
                Thread.sleep(sleep_duration);
            } catch (Exception e) {
            }

        }
    }

//    private int getValidity(String validity){
//        int result = -1;
//        
//        if (validity.toUpperCase().startsWith("D")){
//            String value = validity.replace("D", "");
//            try {
//                result = Integer.parseInt(value);
//            } catch (Exception e) {
//                result = -1;
//            }
//        }else if (validity.toUpperCase().startsWith("H")){
//            String value = validity.replace("H", "");
//            try {
//                result = Integer.parseInt(value);
//            } catch (Exception e) {
//                result = -1;
//            }
//        }
//        
//        
//        return result;
//    } 
//    
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

    private int getDuration(String validity) {
        int result = 0;
        if (validity.toUpperCase().startsWith("D")) {
            String value = validity.replace("D", "");
            result = Integer.parseInt(value);
        } else if (validity.toUpperCase().startsWith("H")) {
            String value = validity.replace("H", "");
            result = Integer.parseInt(value);
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
