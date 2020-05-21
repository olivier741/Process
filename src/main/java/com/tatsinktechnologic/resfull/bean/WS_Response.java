/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.resfull.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.soap.SOAPMessage;

/**
 *
 * @author olivier.tatsinkou
 */
@XmlRootElement(name = "Response")
public class WS_Response implements Serializable{
    
    private String msisdn;
    private String reg_trans_id;
    private String WS_name;
    private String API_GW_Error;
    private String API_GW_Description;
    private String WS_ERROR;
    private String WS_Description;
    private String WS_ResponseContent;
    private String WS_request_time;
    private String Client_IP;

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

    
    
    public String getWS_name() {
        return WS_name;
    }

    public void setWS_name(String WS_name) {
        this.WS_name = WS_name;
    }

    public String getAPI_GW_Error() {
        return API_GW_Error;
    }

    public void setAPI_GW_Error(String API_GW_Error) {
        this.API_GW_Error = API_GW_Error;
    }

    public String getWS_ERROR() {
        return WS_ERROR;
    }

    public void setWS_ERROR(String WS_ERROR) {
        this.WS_ERROR = WS_ERROR;
    }

    public String getAPI_GW_Description() {
        return API_GW_Description;
    }

    public void setAPI_GW_Description(String API_GW_Description) {
        this.API_GW_Description = API_GW_Description;
    }

    public String getWS_Description() {
        return WS_Description;
    }

    public void setWS_Description(String WS_Description) {
        this.WS_Description = WS_Description;
    }

    public String getWS_ResponseContent() {
        return WS_ResponseContent;
    }

    public void setWS_ResponseContent(String WS_ResponseContent) {
        this.WS_ResponseContent = WS_ResponseContent;
    }


    public String getWS_request_time() {
        return WS_request_time;
    }

    public void setWS_request_time(String WS_request_time) {
        this.WS_request_time = WS_request_time;
    }

    public String getClient_IP() {
        return Client_IP;
    }

    public void setClient_IP(String Client_IP) {
        this.Client_IP = Client_IP;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.WS_name);
        hash = 61 * hash + Objects.hashCode(this.API_GW_Error);
        hash = 61 * hash + Objects.hashCode(this.API_GW_Description);
        hash = 61 * hash + Objects.hashCode(this.WS_ERROR);
        hash = 61 * hash + Objects.hashCode(this.WS_Description);
        hash = 61 * hash + Objects.hashCode(this.WS_ResponseContent);
        hash = 61 * hash + Objects.hashCode(this.WS_request_time);
        hash = 61 * hash + Objects.hashCode(this.Client_IP);
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
        final WS_Response other = (WS_Response) obj;
        if (!Objects.equals(this.WS_name, other.WS_name)) {
            return false;
        }
        if (!Objects.equals(this.API_GW_Error, other.API_GW_Error)) {
            return false;
        }
        if (!Objects.equals(this.API_GW_Description, other.API_GW_Description)) {
            return false;
        }
        if (!Objects.equals(this.WS_ERROR, other.WS_ERROR)) {
            return false;
        }
        if (!Objects.equals(this.WS_Description, other.WS_Description)) {
            return false;
        }
        if (!Objects.equals(this.WS_ResponseContent, other.WS_ResponseContent)) {
            return false;
        }
        if (!Objects.equals(this.WS_request_time, other.WS_request_time)) {
            return false;
        }
        if (!Objects.equals(this.Client_IP, other.Client_IP)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "WS_Response{" + "WS_name=" + WS_name + ", API_GW_Error=" + API_GW_Error + ", API_GW_Description=" + API_GW_Description + ", WS_ERROR=" + WS_ERROR + ", WS_Description=" + WS_Description + ", WS_ResponseContent=" + WS_ResponseContent + ", WS_request_time=" + WS_request_time + ", Client_IP=" + Client_IP + '}';
    }

  
}