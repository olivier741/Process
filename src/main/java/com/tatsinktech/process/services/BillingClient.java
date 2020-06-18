/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.process.services;

/**
 *
 * @author olivier
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tatsinktech.process.beans.WS_Request;
import com.tatsinktech.process.beans.WS_Response;
import com.tatsinktech.process.config.Load_Configuration;
import com.tatsinktech.process.model.register.Charge_Event;
import com.tatsinktech.process.model.register.Charge_Hist;
import com.tatsinktech.process.model.repository.Charge_HistRepository;
import com.tatsinktech.process.util.ConverterXML_JSON;
import com.tatsinktech.process.util.Utils;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

@Service
public class BillingClient {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    private Load_Configuration commonConfig;

    @Autowired
    private Charge_HistRepository chargehistRepo;

    private static InetAddress address;

    @PostConstruct
    private void init() {
        BillingClient.address = Utils.gethostName();

    }

    public int charge(WS_Request wsRequest) {
        int result = -1;
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = null;

        try {
            jsonRequest = objectMapper.writeValueAsString(wsRequest);
            logger.info("################## request for Charge #############################");
            logger.info("URL = " + commonConfig.getChargingUrl());
            logger.info(jsonRequest);
        } catch (Exception e) {

        }

        ResponseEntity<String> response = oAuth2RestTemplate.postForEntity(commonConfig.getChargingUrl(), jsonRequest, String.class);

        logger.info("################## response for Charge #############################");
        logger.info(response.getBody());
        HttpStatus httpStatus = response.getStatusCode();
        
        WS_Response ws_response = null;
        
        try {
            ws_response = ConverterXML_JSON.convertJsonToWS_Response(response.getBody());
        } catch (Exception e) {

        }

        String apiGWDescription = null;
        String response_time = null;
        long duration = 0;

        String requestAsString = null;
        String responseAsString = null;

        try {
            requestAsString = objectMapper.writeValueAsString(wsRequest);
            responseAsString = objectMapper.writeValueAsString(ws_response);
        } catch (Exception e) {

        }
        Charge_Hist charge_hist = new Charge_Hist();
        charge_hist.setChargeFee(0);
        switch (httpStatus) {
            case OK:
                apiGWDescription = ws_response.getAPI_GW_Description();
                result = ws_response.getAPI_GW_Error();
                response_time = ws_response.getWS_response_time();
                duration = ws_response.getDuration();

                if (result== 0) { // success charge
                    charge_hist.setChargeFee(wsRequest.getAmount());
                }
                
                if (result== 3) { // time out
                    result=-1;
                }
                
                

                break;
        }
        
        charge_hist.setChargeError(result);
        charge_hist.setChargeMode(Charge_Event.REGISTRATION);
        charge_hist.setChargeRequest(requestAsString);
        charge_hist.setChargeResponse(responseAsString);
        try {
            charge_hist.setChargeTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(response_time));
        } catch (Exception e) {
        }
        charge_hist.setDuration(duration);
        charge_hist.setIpAddress(address.getHostName() + "@" + address.getHostAddress());
        charge_hist.setMsisdn(wsRequest.getMsisdn());
        charge_hist.setProcessUnit(wsRequest.getProcessUnit());
        charge_hist.setProductCode(wsRequest.getProductCode());
        charge_hist.setTransactionId(wsRequest.getTransactionId());
        charge_hist.setDescription(apiGWDescription);

        chargehistRepo.save(charge_hist);
        return result;
    }
}
