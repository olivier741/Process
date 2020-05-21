/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.resfull.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author olivier.tatsinkou
 */
@XmlRootElement(name = "Request")
public class Request_log {
     
    private String msisdn;
    private String reg_trans_id;
    private String user_name;   
    private String password;           
    private Rest_Method method;
    private String host;
    private String path;
    private String rest_request;
    private int connexion_timeout;
    private int request_timeout;
    private Security_Mode sec_mode;
    private Map<String, String> headers1 = new HashMap<String,String>();
    private Map<String, MyList> headers2 = new HashMap<>();
    private String content_type;
    private String media_type;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getReg_trans_id() {
        return reg_trans_id;
    }

    public void setReg_trans_id(String reg_trans_id) {
        this.reg_trans_id = reg_trans_id;
    }

    
    
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rest_Method getMethod() {
        return method;
    }

    public void setMethod(Rest_Method method) {
        this.method = method;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRest_request() {
        return rest_request;
    }

    public void setRest_request(String rest_request) {
        this.rest_request = rest_request;
    }

    public int getConnexion_timeout() {
        return connexion_timeout;
    }

    public void setConnexion_timeout(int connexion_timeout) {
        this.connexion_timeout = connexion_timeout;
    }

    public int getRequest_timeout() {
        return request_timeout;
    }

    public void setRequest_timeout(int request_timeout) {
        this.request_timeout = request_timeout;
    }

    public Security_Mode getSec_mode() {
        return sec_mode;
    }

    public void setSec_mode(Security_Mode sec_mode) {
        this.sec_mode = sec_mode;
    }

    public Map<String, String> getHeaders1() {
        return headers1;
    }

    public void setHeaders1(Map<String, String> headers1) {
        this.headers1 = headers1;
    }

    public Map<String, MyList> getHeaders2() {
        return headers2;
    }

    public void setHeaders2(Map<String, MyList> headers2) {
        this.headers2 = headers2;
    }

   

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.user_name);
        hash = 83 * hash + Objects.hashCode(this.password);
        hash = 83 * hash + Objects.hashCode(this.method);
        hash = 83 * hash + Objects.hashCode(this.host);
        hash = 83 * hash + Objects.hashCode(this.path);
        hash = 83 * hash + Objects.hashCode(this.rest_request);
        hash = 83 * hash + this.connexion_timeout;
        hash = 83 * hash + this.request_timeout;
        hash = 83 * hash + Objects.hashCode(this.sec_mode);
        hash = 83 * hash + Objects.hashCode(this.content_type);
        hash = 83 * hash + Objects.hashCode(this.media_type);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Request_log other = (Request_log) obj;
        if (this.connexion_timeout != other.connexion_timeout) {
            return false;
        }
        if (this.request_timeout != other.request_timeout) {
            return false;
        }
        if (!Objects.equals(this.user_name, other.user_name)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.host, other.host)) {
            return false;
        }
        if (!Objects.equals(this.path, other.path)) {
            return false;
        }
        if (!Objects.equals(this.rest_request, other.rest_request)) {
            return false;
        }
        if (!Objects.equals(this.content_type, other.content_type)) {
            return false;
        }
        if (!Objects.equals(this.media_type, other.media_type)) {
            return false;
        }
        if (this.method != other.method) {
            return false;
        }
        if (this.sec_mode != other.sec_mode) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Request_log{" + "user_name=" + user_name + ", password=" + password + ", method=" + method + ", host=" + host + ", path=" + path + ", rest_request=" + rest_request + ", connexion_timeout=" + connexion_timeout + ", request_timeout=" + request_timeout + ", sec_mode=" + sec_mode + ", content_type=" + content_type + ", media_type=" + media_type + '}';
    }

  
}
