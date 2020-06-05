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
import com.tatsinktech.process.beans.WS_Request;
import com.tatsinktech.process.beans.WS_Response;
import com.tatsinktech.process.config.Load_Configuration;

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
    private static Load_Configuration commonConfig;

    public void charge(WS_Request wsRequest) {
        
        ResponseEntity<WS_Response> response = oAuth2RestTemplate.postForEntity(commonConfig.getChargingUrl(), wsRequest, WS_Response.class);

        HttpStatus httpStatus = response.getStatusCode();
        WS_Response ws_response = response.getBody();
        
        switch (httpStatus) {
            case CREATED:
                String wsDescription = ws_response.getAPI_GW_Description();
                String wsError = ws_response.getAPI_GW_Error();
                String wsContent = ws_response.getWS_ResponseContent();
                String request_time = ws_response.getWS_request_time();
                String response_time = ws_response.getWS_response_time();
                
                break;
            case BAD_REQUEST:

                break;
        }

        System.out.println("response status = " + response.getStatusCode());
        System.out.println("response status value = " + response.getStatusCodeValue());
        System.out.println("response Body = " + response.getBody());
    }
}
