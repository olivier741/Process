/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.process.billing;

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
import java.net.InetAddress;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

@Configuration
public class BillingClient {

    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    private Load_Configuration commonConfig;

    @Autowired
    private Charge_HistRepository chargehistRepo;
    
    private InetAddress address;

    public int charge(WS_Request wsRequest) {
        int result = -1;
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseEntity<WS_Response> response = oAuth2RestTemplate.postForEntity(commonConfig.getChargingUrl(), wsRequest, WS_Response.class);

        HttpStatus httpStatus = response.getStatusCode();
        WS_Response ws_response = response.getBody();

        String apiGWDescription = null;
        String apiGWError = null;
        String wsError = null;
        String wsContent = null;
        String request_time = null;
        String response_time = null;
        long duration = 0;

        switch (httpStatus) {
            case CREATED:
                apiGWDescription = ws_response.getAPI_GW_Description();
                apiGWError = ws_response.getAPI_GW_Error();
                wsError = ws_response.getWS_Error();
                wsContent = ws_response.getWS_ResponseContent();
                request_time = ws_response.getWS_request_time();
                response_time = ws_response.getWS_response_time();
                duration = ws_response.getDuration();

                
                if (apiGWError.equals("00")) { // success charge
                    result = 0;
                }

                if (apiGWError.equals("01")) { // not enough money
                    result = 1;
                }
                
                 if (apiGWError.equals("02")) { // customer is wrong status (inactive or block)
                    result = 2;
                }
                break;
            case BAD_REQUEST:

                break;
        }
        String requestAsString = null;
        String responseAsString = null;
        try {
        requestAsString = objectMapper.writeValueAsString(wsRequest);
        responseAsString = objectMapper.writeValueAsString(ws_response);
        } catch (Exception e) {
            
        }
        

        Charge_Hist charge_hist = new Charge_Hist();
        charge_hist.setChargeError(apiGWError);
        charge_hist.setChargeFee(wsRequest.getAmount());
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
