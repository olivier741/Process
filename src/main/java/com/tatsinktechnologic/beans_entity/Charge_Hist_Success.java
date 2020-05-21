///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tatsinktechnologic.beans_entity;
//
//import java.io.Serializable;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import java.sql.Timestamp;
//import java.util.Objects;
//import javax.persistence.Column;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.Lob;
//import javax.persistence.Transient;
//import org.hibernate.annotations.Check;
//import org.apache.log4j.Logger;
//import org.hibernate.annotations.UpdateTimestamp;
//
///**
// *
// * @author olivier
// */
//@Entity
//@Check(constraints = "charge_fee >= 0")
//public class Charge_Hist_Success implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    
//    @Transient
//    private Logger logger = Logger.getLogger(Charge_Hist_Success.class);
//    
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(unique = true, nullable = false)
//    private int charge_his_id;
//
//    private String transaction_id;
//    private String msisdn;
//    
//    @Enumerated(EnumType.STRING)
//    private Charge_Event charge_mode;
//    
//    @UpdateTimestamp
//    private Timestamp charge_time;
//    private long charge_fee;
//    
//    @Lob 
//    private String charge_request;
//    
//    @Lob 
//    private String charge_response;
//    
//    private long duration;
//     
//    private String charge_error;
//    
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "product_id", nullable = true)
//    private Product product;
//    
//    private String process_unit;
//    private String IP_unit;
//
//    public int getCharge_his_id() {
//        return charge_his_id;
//    }
//
//    public String getTransaction_id() {
//        return transaction_id;
//    }
//
//    public String getMsisdn() {
//        return msisdn;
//    }
//
//    public Charge_Event getCharge_mode() {
//        return charge_mode;
//    }
//
//    public Timestamp getCharge_time() {
//        return charge_time;
//    }
//
//   
//    public long getCharge_fee() {
//        return charge_fee;
//    }
//
//    public String getCharge_request() {
//        return charge_request;
//    }
//
//    public String getCharge_response() {
//        return charge_response;
//    }
//
//    public long getDuration() {
//        return duration;
//    }
//
//    public String getCharge_error() {
//        return charge_error;
//    }
//
//    public Product getProduct() {
//        return product;
//    }
//
//    public String getProcess_unit() {
//        return process_unit;
//    }
//
//    public String getIP_unit() {
//        return IP_unit;
//    }
//
//    public void setCharge_his_id(int charge_his_id) {
//        this.charge_his_id = charge_his_id;
//    }
//
//    public void setTransaction_id(String transaction_id) {
//        this.transaction_id = transaction_id;
//    }
//
//    public void setMsisdn(String msisdn) {
//        this.msisdn = msisdn;
//    }
//
//    public void setCharge_mode(Charge_Event charge_mode) {
//        this.charge_mode = charge_mode;
//    }
//
//    public void setCharge_time(Timestamp charge_time) {
//        this.charge_time = charge_time;
//    }
//
//    public void setCharge_fee(long charge_fee) {
//        this.charge_fee = charge_fee;
//    }
//
//    public void setCharge_request(String charge_request) {
//        this.charge_request = charge_request;
//    }
//
//    public void setCharge_response(String charge_response) {
//        this.charge_response = charge_response;
//    }
//
//    public void setDuration(long duration) {
//        this.duration = duration;
//    }
//
//    public void setCharge_error(String charge_error) {
//        this.charge_error = charge_error;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }
//
//    public void setProcess_unit(String process_unit) {
//        this.process_unit = process_unit;
//    }
//
//    public void setIP_unit(String IP_unit) {
//        this.IP_unit = IP_unit;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 5;
//        hash = 59 * hash + Objects.hashCode(this.charge_his_id);
//        hash = 59 * hash + Objects.hashCode(this.transaction_id);
//        hash = 59 * hash + Objects.hashCode(this.msisdn);
//        hash = 59 * hash + Objects.hashCode(this.charge_mode);
//        hash = 59 * hash + Objects.hashCode(this.charge_time);
//        hash = 59 * hash + (int) (this.charge_fee ^ (this.charge_fee >>> 32));
//        hash = 59 * hash + Objects.hashCode(this.charge_request);
//        hash = 59 * hash + Objects.hashCode(this.charge_response);
//        hash = 59 * hash + (int) (this.duration ^ (this.duration >>> 32));
//        hash = 59 * hash + Objects.hashCode(this.charge_error);
//        hash = 59 * hash + Objects.hashCode(this.product);
//        hash = 59 * hash + Objects.hashCode(this.process_unit);
//        hash = 59 * hash + Objects.hashCode(this.IP_unit);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final Charge_Hist_Success other = (Charge_Hist_Success) obj;
//        if (this.charge_fee != other.charge_fee) {
//            return false;
//        }
//        if (this.duration != other.duration) {
//            return false;
//        }
//        if (!Objects.equals(this.transaction_id, other.transaction_id)) {
//            return false;
//        }
//        if (!Objects.equals(this.msisdn, other.msisdn)) {
//            return false;
//        }
//        if (!Objects.equals(this.charge_request, other.charge_request)) {
//            return false;
//        }
//        if (!Objects.equals(this.charge_response, other.charge_response)) {
//            return false;
//        }
//        if (!Objects.equals(this.charge_error, other.charge_error)) {
//            return false;
//        }
//        if (!Objects.equals(this.process_unit, other.process_unit)) {
//            return false;
//        }
//        if (!Objects.equals(this.IP_unit, other.IP_unit)) {
//            return false;
//        }
//        if (!Objects.equals(this.charge_his_id, other.charge_his_id)) {
//            return false;
//        }
//        if (this.charge_mode != other.charge_mode) {
//            return false;
//        }
//        if (!Objects.equals(this.charge_time, other.charge_time)) {
//            return false;
//        }
//        if (!Objects.equals(this.product, other.product)) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "Charge_Hist_Success{" + "charge_his_id=" + charge_his_id + ", transaction_id=" + transaction_id + ", msisdn=" + msisdn + ", charge_mode=" + charge_mode + ", charge_time=" + charge_time + ", charge_fee=" + charge_fee + ", charge_request=" + charge_request + ", charge_response=" + charge_response + ", duration=" + duration + ", charge_error=" + charge_error + ", product=" + product + ", process_unit=" + process_unit + ", IP_unit=" + IP_unit + '}';
//    }
//
// 
//}
