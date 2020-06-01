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
public class Product {

   private int product_id;
   private String product_name;
   private int reg_fee;
   private Timestamp start_time;
   private Timestamp end_time;
   private String register_time;
   private String register_day;
   private String restrict_product;
   private int isextend;
   private int extend_fee;
   private String list_allow;
   private String pending_time;
   private String validity;

    public int getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public int getReg_fee() {
        return reg_fee;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public Timestamp getEnd_time() {
        return end_time;
    }

    public String getRegister_time() {
        return register_time;
    }

    public String getRegister_day() {
        return register_day;
    }

    public String getRestrict_product() {
        return restrict_product;
    }

    public int getIsextend() {
        return isextend;
    }

    public int getExtend_fee() {
        return extend_fee;
    }

    public String getList_allow() {
        return list_allow;
    }

    public String getPending_time() {
        return pending_time;
    }

    public String getValidity() {
        return validity;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setReg_fee(int reg_fee) {
        this.reg_fee = reg_fee;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public void setRegister_day(String register_day) {
        this.register_day = register_day;
    }

    public void setRestrict_product(String restrict_product) {
        this.restrict_product = restrict_product;
    }

    public void setIsextend(int isextend) {
        this.isextend = isextend;
    }

    public void setExtend_fee(int extend_fee) {
        this.extend_fee = extend_fee;
    }

    public void setList_allow(String list_allow) {
        this.list_allow = list_allow;
    }

    public void setPending_time(String pending_time) {
        this.pending_time = pending_time;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.product_id;
        hash = 19 * hash + (this.product_name != null ? this.product_name.hashCode() : 0);
        hash = 19 * hash + this.reg_fee;
        hash = 19 * hash + (this.start_time != null ? this.start_time.hashCode() : 0);
        hash = 19 * hash + (this.end_time != null ? this.end_time.hashCode() : 0);
        hash = 19 * hash + (this.register_time != null ? this.register_time.hashCode() : 0);
        hash = 19 * hash + (this.register_day != null ? this.register_day.hashCode() : 0);
        hash = 19 * hash + (this.restrict_product != null ? this.restrict_product.hashCode() : 0);
        hash = 19 * hash + this.isextend;
        hash = 19 * hash + this.extend_fee;
        hash = 19 * hash + (this.list_allow != null ? this.list_allow.hashCode() : 0);
        hash = 19 * hash + (this.pending_time != null ? this.pending_time.hashCode() : 0);
        hash = 19 * hash + (this.validity != null ? this.validity.hashCode() : 0);
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
        final Product other = (Product) obj;
        if (this.product_id != other.product_id) {
            return false;
        }
        if (this.reg_fee != other.reg_fee) {
            return false;
        }
        if (this.isextend != other.isextend) {
            return false;
        }
        if (this.extend_fee != other.extend_fee) {
            return false;
        }
        if ((this.product_name == null) ? (other.product_name != null) : !this.product_name.equals(other.product_name)) {
            return false;
        }
        if ((this.register_time == null) ? (other.register_time != null) : !this.register_time.equals(other.register_time)) {
            return false;
        }
        if ((this.register_day == null) ? (other.register_day != null) : !this.register_day.equals(other.register_day)) {
            return false;
        }
        if ((this.restrict_product == null) ? (other.restrict_product != null) : !this.restrict_product.equals(other.restrict_product)) {
            return false;
        }
        if ((this.list_allow == null) ? (other.list_allow != null) : !this.list_allow.equals(other.list_allow)) {
            return false;
        }
        if ((this.pending_time == null) ? (other.pending_time != null) : !this.pending_time.equals(other.pending_time)) {
            return false;
        }
        if ((this.validity == null) ? (other.validity != null) : !this.validity.equals(other.validity)) {
            return false;
        }
        if (this.start_time != other.start_time && (this.start_time == null || !this.start_time.equals(other.start_time))) {
            return false;
        }
        if (this.end_time != other.end_time && (this.end_time == null || !this.end_time.equals(other.end_time))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Product{" + "product_id=" + product_id + ", product_name=" + product_name + ", reg_fee=" + reg_fee + ", start_time=" + start_time + ", end_time=" + end_time + ", register_time=" + register_time + ", register_day=" + register_day + ", restrict_product=" + restrict_product + ", isextend=" + isextend + ", extend_fee=" + extend_fee + ", list_allow=" + list_allow + ", pending_time=" + pending_time + ", validity=" + validity + '}';
    }

   
   
}
