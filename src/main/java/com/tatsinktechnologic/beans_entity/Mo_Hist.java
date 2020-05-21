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
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import org.hibernate.annotations.UpdateTimestamp;
//
//
///**
// *
// * @author olivier
// */
//@Entity
//public class Mo_Hist implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(unique = true, nullable = false)
//    private int mo_his_id;
//    
//    private String transaction_id;
//    private String msisdn;
//    private String content;
//    
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "command_id", nullable = true)
//    private Command command;
//    
//    private String channel;
//    
//    private Timestamp receive_time;
//    
//    @UpdateTimestamp
//    private Timestamp process_time;
//    
//    @Enumerated(EnumType.STRING)
//    private Action_Type action_type;
//    
//    private long charge_fee;
//    private int charge_status;
//    private String charge_error;
//    private Timestamp charge_time;
//    private long duration;
//    private String process_unit;
//    private String IP_unit;
//    private String error_description;
//    private String exchange_mode;
//    
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "product_id", nullable = true)
//    private Product product;
//
//    public int getMo_his_id() {
//        return mo_his_id;
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
//    public String getContent() {
//        return content;
//    }
//
//    public Command getCommand() {
//        return command;
//    }
//
//    public String getChannel() {
//        return channel;
//    }
//
//    public Timestamp getReceive_time() {
//        return receive_time;
//    }
//
//    public Timestamp getProcess_time() {
//        return process_time;
//    }
//
//    public Action_Type getAction_type() {
//        return action_type;
//    }
//
//    public long getCharge_fee() {
//        return charge_fee;
//    }
//
//    public int getCharge_status() {
//        return charge_status;
//    }
//
//    public String getCharge_error() {
//        return charge_error;
//    }
//
//    public Timestamp getCharge_time() {
//        return charge_time;
//    }
//
//   
//
//    public long getDuration() {
//        return duration;
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
//    public String getError_description() {
//        return error_description;
//    }
//
//    public Product getProduct() {
//        return product;
//    }
//
//    public void setMo_his_id(int mo_his_id) {
//        this.mo_his_id = mo_his_id;
//    }
//
//    public void setTransaction_id(String transaction_id) {
//        this.transaction_id = transaction_id;
//    }
//
//    
//    public void setMsisdn(String msisdn) {
//        this.msisdn = msisdn;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public void setCommand(Command command) {
//        this.command = command;
//    }
//
//    public void setChannel(String channel) {
//        this.channel = channel;
//    }
//
//    public void setReceive_time(Timestamp receive_time) {
//        this.receive_time = receive_time;
//    }
//
//    public void setProcess_time(Timestamp process_time) {
//        this.process_time = process_time;
//    }
//
//    public void setAction_type(Action_Type action_type) {
//        this.action_type = action_type;
//    }
//
//    public void setCharge_fee(long charge_fee) {
//        this.charge_fee = charge_fee;
//    }
//
//    public void setCharge_status(int charge_status) {
//        this.charge_status = charge_status;
//    }
//
//   
//    public void setCharge_error(String charge_error) {
//        this.charge_error = charge_error;
//    }
//
//    public void setCharge_time(Timestamp charge_time) {
//        this.charge_time = charge_time;
//    }
//
//    
//
//    public void setDuration(long duration) {
//        this.duration = duration;
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
//    public void setError_description(String error_description) {
//        this.error_description = error_description;
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
//        int hash = 5;
//        hash = 41 * hash + this.mo_his_id;
//        hash = 41 * hash + Objects.hashCode(this.transaction_id);
//        hash = 41 * hash + Objects.hashCode(this.msisdn);
//        hash = 41 * hash + Objects.hashCode(this.content);
//        hash = 41 * hash + Objects.hashCode(this.command);
//        hash = 41 * hash + Objects.hashCode(this.channel);
//        hash = 41 * hash + Objects.hashCode(this.receive_time);
//        hash = 41 * hash + Objects.hashCode(this.process_time);
//        hash = 41 * hash + Objects.hashCode(this.action_type);
//        hash = 41 * hash + (int) (this.charge_fee ^ (this.charge_fee >>> 32));
//        hash = 41 * hash + this.charge_status;
//        hash = 41 * hash + Objects.hashCode(this.charge_error);
//        hash = 41 * hash + Objects.hashCode(this.charge_time);
//        hash = 41 * hash + (int) (this.duration ^ (this.duration >>> 32));
//        hash = 41 * hash + Objects.hashCode(this.process_unit);
//        hash = 41 * hash + Objects.hashCode(this.IP_unit);
//        hash = 41 * hash + Objects.hashCode(this.error_description);
//        hash = 41 * hash + Objects.hashCode(this.exchange_mode);
//        hash = 41 * hash + Objects.hashCode(this.product);
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
//        final Mo_Hist other = (Mo_Hist) obj;
//        if (this.mo_his_id != other.mo_his_id) {
//            return false;
//        }
//        if (this.charge_fee != other.charge_fee) {
//            return false;
//        }
//        if (this.charge_status != other.charge_status) {
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
//        if (!Objects.equals(this.content, other.content)) {
//            return false;
//        }
//        if (!Objects.equals(this.channel, other.channel)) {
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
//        if (!Objects.equals(this.error_description, other.error_description)) {
//            return false;
//        }
//        if (!Objects.equals(this.exchange_mode, other.exchange_mode)) {
//            return false;
//        }
//        if (!Objects.equals(this.command, other.command)) {
//            return false;
//        }
//        if (!Objects.equals(this.receive_time, other.receive_time)) {
//            return false;
//        }
//        if (!Objects.equals(this.process_time, other.process_time)) {
//            return false;
//        }
//        if (this.action_type != other.action_type) {
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
//        return "Mo_Hist{" + "mo_his_id=" + mo_his_id + ", transaction_id=" + transaction_id + ", msisdn=" + msisdn + ", content=" + content + ", command=" + command + ", channel=" + channel + ", receive_time=" + receive_time + ", process_time=" + process_time + ", action_type=" + action_type + ", charge_fee=" + charge_fee + ", charge_status=" + charge_status + ", charge_error=" + charge_error + ", charge_time=" + charge_time + ", duration=" + duration + ", process_unit=" + process_unit + ", IP_unit=" + IP_unit + ", error_description=" + error_description + ", exchange_mode=" + exchange_mode + ", product=" + product + '}';
//    }
// 
//
//}
