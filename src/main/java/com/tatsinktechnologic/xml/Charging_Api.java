/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author olivier.tatsinkou
 */
@Root(name="charging_api")
public class Charging_Api {
    @Element(name="url",required = true) 
    private String url;
    
    @Element(name="uri_auth",required = true) 
    private String uri_auth;    
    
    @Element(name="uri_service",required = true) 
    private String uri_service;
    
    @Element(name="client_name",required = true) 
    private String client_name;
    
    @Element(name="user",required = true) 
    private String user;
    
    @Element(name="password",required = true) 
    private String password;
    
    @Element(name="webservice_name",required = true) 
    private String webservice_name;
    
    @Element(name="webservice_con_timeout",required = true) 
    private int webservice_con_timeout;
    
    @Element(name="webservice_req_timeout",required = true) 
    private int webservice_req_timeout;
    
    @Element(name="alias",required = true) 
    private Alias alias;
    
    public Charging_Api(@Element(name="url",required = true) String url, 
            @Element(name="uri_auth",required = true) String uri_auth, 
            @Element(name="uri_service",required = true) String uri_service,  
            @Element(name="client_name",required = true) String client_name, 
            @Element(name="user",required = true)  String user,
            @Element(name="password",required = true) String password, 
            @Element(name="webservice_name",required = true) String webservice_name,
            @Element(name="webservice_con_timeout",required = true) int webservice_con_timeout,
            @Element(name="webservice_req_timeout",required = true) int webservice_req_timeout,
            @Element(name="alias",required = true)  Alias alias) {
        this.url = url;
        this.uri_auth = uri_auth;
        this.uri_service = uri_service;
        this.user=user;
        this.client_name = client_name;
        this.password = password;
        this.webservice_name = webservice_name;
        this.webservice_con_timeout=webservice_con_timeout;
        this.webservice_req_timeout=webservice_req_timeout;
        this.alias=alias;
    }

    public String getUrl() {
        return url;
    }

    public String getUri_auth() {
        return uri_auth;
    }

    public String getUri_service() {
        return uri_service;
    }

    public String getClient_name() {
        return client_name;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getWebservice_name() {
        return webservice_name;
    }

    public int getWebservice_con_timeout() {
        return webservice_con_timeout;
    }

    public int getWebservice_req_timeout() {
        return webservice_req_timeout;
    }

    public Alias getAlias() {
        return alias;
    }

  
}
