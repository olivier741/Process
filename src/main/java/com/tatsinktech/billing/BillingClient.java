/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.billing;

/**
 *
 * @author olivier
 */
import com.tatsinktech.config.HttpClientConfig;
import com.tatsinktech.config.RestTemplateConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = { RestTemplateConfig.class, HttpClientConfig.class })
public class BillingClient {
 
    @Autowired
    RestTemplate restTemplate;
 
    public void charge() {
        final String uri = "http://localhost:8080/employees";
 
        String result = restTemplate.getForObject(uri, String.class);
    }
}
