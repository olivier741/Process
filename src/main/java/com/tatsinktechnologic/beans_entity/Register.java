///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tatsinktechnologic.beans_entity;
//
//import java.io.Serializable;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import java.sql.Timestamp;
//import java.util.Objects;
//import javax.persistence.Column;
//import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//import javax.persistence.UniqueConstraint;
//import org.hibernate.annotations.UpdateTimestamp;
//
//
///**
// *
// * @author olivier
// */
//@Entity
//@Table(name = "register",uniqueConstraints={@UniqueConstraint(columnNames = {"transaction_id","msisdn" , "product_id"})})
//public class Register implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(unique = true, nullable = false)
//    private int register_id;
//
//    private String transaction_id;
//    private String msisdn;
//    private int status;             // 1 = active|active, 0 = active|cancel, 2=active|pending, -1=cancel|cancel (state in network|state in service)
//    private boolean autoextend;
//    
//    private Timestamp reg_time;
//    
//    @UpdateTimestamp
//    private Timestamp Renew_time;
//    
//    private Timestamp expire_time;
//    private Timestamp unreg_time;
//    private Timestamp cancel_time;
//    
//    private int number_reg;
//    
//    private String exchange_mode;
//    
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "product_id", nullable = true)
//    private Product product;
//
//    public int getRegister_id() {
//        return register_id;
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
//    public int getStatus() {
//        return status;
//    }
//
//    public boolean isAutoextend() {
//        return autoextend;
//    }
//
//    public Timestamp getReg_time() {
//        return reg_time;
//    }
//
//    public Timestamp getRenew_time() {
//        return Renew_time;
//    }
//
//    public Timestamp getExpire_time() {
//        return expire_time;
//    }
//
//    public Timestamp getUnreg_time() {
//        return unreg_time;
//    }
//
//    public Timestamp getCancel_time() {
//        return cancel_time;
//    }
//
//    
//    public int getNumber_reg() {
//        return number_reg;
//    }
//
//    public Product getProduct() {
//        return product;
//    }
//
//    public void setRegister_id(int register_id) {
//        this.register_id = register_id;
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
//    public void setStatus(int status) {
//        this.status = status;
//    }
//
//    public void setAutoextend(boolean autoextend) {
//        this.autoextend = autoextend;
//    }
//
//    public void setReg_time(Timestamp reg_time) {
//        this.reg_time = reg_time;
//    }
//
//    public void setRenew_time(Timestamp Renew_time) {
//        this.Renew_time = Renew_time;
//    }
//
//    public void setExpire_time(Timestamp expire_time) {
//        this.expire_time = expire_time;
//    }
//
//    public void setUnreg_time(Timestamp unreg_time) {
//        this.unreg_time = unreg_time;
//    }
//
//    public void setCancel_time(Timestamp cancel_time) {
//        this.cancel_time = cancel_time;
//    }
//    
//    
//
//    public void setNumber_reg(int number_reg) {
//        this.number_reg = number_reg;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }
//
//    public String getExchange_mode() {
//        return exchange_mode;
//    }
//
//    public void setExchange_mode(String exchange_mode) {
//        this.exchange_mode = exchange_mode;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 71 * hash + this.register_id;
//        hash = 71 * hash + Objects.hashCode(this.transaction_id);
//        hash = 71 * hash + Objects.hashCode(this.msisdn);
//        hash = 71 * hash + this.status;
//        hash = 71 * hash + (this.autoextend ? 1 : 0);
//        hash = 71 * hash + Objects.hashCode(this.reg_time);
//        hash = 71 * hash + Objects.hashCode(this.Renew_time);
//        hash = 71 * hash + Objects.hashCode(this.expire_time);
//        hash = 71 * hash + Objects.hashCode(this.unreg_time);
//        hash = 71 * hash + Objects.hashCode(this.cancel_time);
//        hash = 71 * hash + this.number_reg;
//        hash = 71 * hash + Objects.hashCode(this.exchange_mode);
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
//        final Register other = (Register) obj;
//        if (this.register_id != other.register_id) {
//            return false;
//        }
//        if (this.status != other.status) {
//            return false;
//        }
//        if (this.autoextend != other.autoextend) {
//            return false;
//        }
//        if (this.number_reg != other.number_reg) {
//            return false;
//        }
//        if (!Objects.equals(this.transaction_id, other.transaction_id)) {
//            return false;
//        }
//        if (!Objects.equals(this.msisdn, other.msisdn)) {
//            return false;
//        }
//        if (!Objects.equals(this.exchange_mode, other.exchange_mode)) {
//            return false;
//        }
//        if (!Objects.equals(this.reg_time, other.reg_time)) {
//            return false;
//        }
//        if (!Objects.equals(this.Renew_time, other.Renew_time)) {
//            return false;
//        }
//        if (!Objects.equals(this.expire_time, other.expire_time)) {
//            return false;
//        }
//        if (!Objects.equals(this.unreg_time, other.unreg_time)) {
//            return false;
//        }
//        if (!Objects.equals(this.cancel_time, other.cancel_time)) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "Register{" + "register_id=" + register_id + ", transaction_id=" + transaction_id + ", msisdn=" + msisdn + ", status=" + status + ", autoextend=" + autoextend + ", reg_time=" + reg_time + ", Renew_time=" + Renew_time + ", expire_time=" + expire_time + ", unreg_time=" + unreg_time + ", cancel_time=" + cancel_time + ", number_reg=" + number_reg + ", exchange_mode=" + exchange_mode + '}';
//    }
//       
//
//}
