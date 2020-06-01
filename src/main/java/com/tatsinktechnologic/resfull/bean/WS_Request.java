/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.resfull.bean;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author olivier.tatsinkou
 */
@XmlRootElement(name = "Request")
public class WS_Request  implements Serializable{
    private String ws_client_name;
    private String msisdn;
    private long charge_amount;
    private String charge_reason;
    private String transaction_id;
    private String ws_block_api_name;
    private List<Param> WSparam = new ArrayList<Param>();

    public long getCharge_amount() {
        return charge_amount;
    }

    public void setCharge_amount(long charge_amount) {
        this.charge_amount = charge_amount;
    }

    public String getCharge_reason() {
        return charge_reason;
    }

    public void setCharge_reason(String charge_reason) {
        this.charge_reason = charge_reason;
    }

    
    
    
    public String getWs_client_name() {
        return ws_client_name;
    }

    public void setWs_client_name(String ws_client_name) {
        this.ws_client_name = ws_client_name;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getReg_transaction_id() {
        return Reg_transaction_id;
    }

    public void setReg_transaction_id(String Reg_transaction_id) {
        this.Reg_transaction_id = Reg_transaction_id;
    }
    
    

    public String getWs_block_api_name() {
        return ws_block_api_name;
    }

    public void setWs_block_api_name(String ws_block_api_name) {
        this.ws_block_api_name = ws_block_api_name;
    }

    public List<Param> getWSparam() {
        return WSparam;
    }

    public void setWSparam(List<Param> WSparam) {
        this.WSparam = WSparam;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.ws_client_name);
        hash = 67 * hash + Objects.hashCode(this.msisdn);
        hash = 67 * hash + (int) (this.charge_amount ^ (this.charge_amount >>> 32));
        hash = 67 * hash + Objects.hashCode(this.charge_reason);
        hash = 67 * hash + Objects.hashCode(this.Reg_transaction_id);
        hash = 67 * hash + Objects.hashCode(this.ws_block_api_name);
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
        final WS_Request other = (WS_Request) obj;
        if (this.charge_amount != other.charge_amount) {
            return false;
        }
        if (!Objects.equals(this.ws_client_name, other.ws_client_name)) {
            return false;
        }
        if (!Objects.equals(this.msisdn, other.msisdn)) {
            return false;
        }
        if (!Objects.equals(this.charge_reason, other.charge_reason)) {
            return false;
        }
        if (!Objects.equals(this.Reg_transaction_id, other.Reg_transaction_id)) {
            return false;
        }
        if (!Objects.equals(this.ws_block_api_name, other.ws_block_api_name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "WS_Request{" + "ws_client_name=" + ws_client_name + ", msisdn=" + msisdn + ", charge_amount=" + charge_amount + ", charge_reason=" + charge_reason + ", Reg_transaction_id=" + Reg_transaction_id + ", ws_block_api_name=" + ws_block_api_name + '}';
    }

   
}
