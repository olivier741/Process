/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.process.beans;

import java.sql.Timestamp;

/**
 *
 * @author olivier.tatsinkou
 */
public class Sub_Register {
    private int id;
    private String msisdn;
    private String product_name;
    private int fee;
    private Timestamp start_time;
    private Timestamp expire_time;
    private Timestamp end_time;
    private Timestamp paid_time;
    private Timestamp delete_time;
    private String validity;
    private int status;
    private int auto_extend;
    private String restrict_product;

    public int getId() {
        return id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public String getProduct_name() {
        return product_name;
    }

    public int getFee() {
        return fee;
    }
    

    public Timestamp getStart_time() {
        return start_time;
    }

    public Timestamp getExpire_time() {
        return expire_time;
    }

    public Timestamp getEnd_time() {
        return end_time;
    }

    public Timestamp getPaid_time() {
        return paid_time;
    }

    public Timestamp getDelete_time() {
        return delete_time;
    }
    

    public String getValidity() {
        return validity;
    }

    public int getStatus() {
        return status;
    }

    public int getAuto_extend() {
        return auto_extend;
    }

    public String getRestrict_product() {
        return restrict_product;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    
    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public void setExpire_time(Timestamp expire_time) {
        this.expire_time = expire_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }

    public void setPaid_time(Timestamp paid_time) {
        this.paid_time = paid_time;
    }

    public void setDelete_time(Timestamp delete_time) {
        this.delete_time = delete_time;
    }
    
    

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setAuto_extend(int auto_extend) {
        this.auto_extend = auto_extend;
    }

    public void setRestrict_product(String restrict_product) {
        this.restrict_product = restrict_product;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + this.id;
        hash = 89 * hash + (this.msisdn != null ? this.msisdn.hashCode() : 0);
        hash = 89 * hash + (this.product_name != null ? this.product_name.hashCode() : 0);
        hash = 89 * hash + this.fee;
        hash = 89 * hash + (this.start_time != null ? this.start_time.hashCode() : 0);
        hash = 89 * hash + (this.expire_time != null ? this.expire_time.hashCode() : 0);
        hash = 89 * hash + (this.end_time != null ? this.end_time.hashCode() : 0);
        hash = 89 * hash + (this.paid_time != null ? this.paid_time.hashCode() : 0);
        hash = 89 * hash + (this.delete_time != null ? this.delete_time.hashCode() : 0);
        hash = 89 * hash + (this.validity != null ? this.validity.hashCode() : 0);
        hash = 89 * hash + this.status;
        hash = 89 * hash + this.auto_extend;
        hash = 89 * hash + (this.restrict_product != null ? this.restrict_product.hashCode() : 0);
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
        final Sub_Register other = (Sub_Register) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.fee != other.fee) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (this.auto_extend != other.auto_extend) {
            return false;
        }
        if ((this.msisdn == null) ? (other.msisdn != null) : !this.msisdn.equals(other.msisdn)) {
            return false;
        }
        if ((this.product_name == null) ? (other.product_name != null) : !this.product_name.equals(other.product_name)) {
            return false;
        }
        if ((this.validity == null) ? (other.validity != null) : !this.validity.equals(other.validity)) {
            return false;
        }
        if ((this.restrict_product == null) ? (other.restrict_product != null) : !this.restrict_product.equals(other.restrict_product)) {
            return false;
        }
        if (this.start_time != other.start_time && (this.start_time == null || !this.start_time.equals(other.start_time))) {
            return false;
        }
        if (this.expire_time != other.expire_time && (this.expire_time == null || !this.expire_time.equals(other.expire_time))) {
            return false;
        }
        if (this.end_time != other.end_time && (this.end_time == null || !this.end_time.equals(other.end_time))) {
            return false;
        }
        if (this.paid_time != other.paid_time && (this.paid_time == null || !this.paid_time.equals(other.paid_time))) {
            return false;
        }
        if (this.delete_time != other.delete_time && (this.delete_time == null || !this.delete_time.equals(other.delete_time))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sub_Register{" + "id=" + id + ", msisdn=" + msisdn + ", product_name=" + product_name + ", fee=" + fee + ", start_time=" + start_time + ", expire_time=" + expire_time + ", end_time=" + end_time + ", paid_time=" + paid_time + ", delete_time=" + delete_time + ", validity=" + validity + ", status=" + status + ", auto_extend=" + auto_extend + ", restrict_product=" + restrict_product + '}';
    }

  
}
