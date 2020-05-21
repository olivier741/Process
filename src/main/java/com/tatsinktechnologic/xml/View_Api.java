/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.xml;

import java.util.ArrayList;
import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 *
 * @author olivier.tatsinkou
 */
@Root(name="view_api")
public class View_Api {
    @Element(name="url",required = true) 
    private String url;
    
    @Element(name="uri_auth",required = true) 
    private String uri_auth;    
    
    @Element(name="uri_service",required = true) 
    private String uri_service;
    
    @Element(name="client_name",required = true) 
    private String client_name;
    
    @Element(name="password",required = true) 
    private String password;
    
    @Element(name="webservice_name",required = true) 
    private String webservice_name;
    
    @Element(name="webservice_timeout",required = true) 
    private int webservice_timeout;
    
    @ElementList(inline=true,entry="tag",required = true)
    private List<Alias> listTag = new ArrayList<Alias>();

    public View_Api(@Element(name="url",required = true) String url, 
            @Element(name="uri_auth",required = true) String uri_auth, 
            @Element(name="uri_service",required = true) String uri_service,  
            @Element(name="client_name",required = true) String client_name, 
            @Element(name="password",required = true) String password, 
            @Element(name="webservice_name",required = true) String webservice_name,
            @Element(name="webservice_timeout",required = true) int webservice_timeout,
            @ElementList(inline=true, entry="tag" ,required = true) List<Alias> listTag) {
        this.url = url;
        this.uri_auth = uri_auth;
        this.uri_service = uri_service;
        this.client_name = client_name;
        this.password = password;
        this.webservice_name = webservice_name;
        this.webservice_timeout=webservice_timeout;
        this.listTag=listTag;
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

    public String getPassword() {
        return password;
    }

    public String getWebservice_name() {
        return webservice_name;
    }

    public int getWebservice_timeout() {
        return webservice_timeout;
    }

    public List<Alias> getListTag() {
        return listTag;
    }

  
}
