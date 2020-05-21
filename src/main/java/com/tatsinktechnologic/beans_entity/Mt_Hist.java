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
//import java.sql.Date;
//import java.sql.Timestamp;
//import java.util.Objects;
//import javax.persistence.Column;
//import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinColumns;
//import javax.persistence.Lob;
//import javax.persistence.ManyToOne;
//import javax.persistence.Transient;
//import org.apache.log4j.Logger;
//import org.hibernate.annotations.UpdateTimestamp;
//
///**
// *
// * @author olivier
// */
//@Entity
//public class Mt_Hist implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    
//    @Transient
//    private Logger logger = Logger.getLogger(Mt_Hist.class);
//    
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(unique = true, nullable = false)
//    private int mt_his_id;
//    
//    private String transaction_id;
//    private String msisdn;
//    
//    @Lob 
//    private String message;
//    
//    private String channel;
//    private Timestamp receive_time;
//    
//    @UpdateTimestamp
//    private Timestamp send_time;
//    
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "command_id", nullable = true)
//    private Command command;
//    
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "product_id", nullable = true)
//    private Product product;
//   
//    private String process_unit;
//    private String IP_unit;
//
//    public int getMt_his_id() {
//        return mt_his_id;
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
//    public String getMessage() {
//        return message;
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
//    public Timestamp getSend_time() {
//        return send_time;
//    }
//
//    public Command getCommand() {
//        return command;
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
//    public void setMt_his_id(int mt_his_id) {
//        this.mt_his_id = mt_his_id;
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
//    public void setMessage(String message) {
//        this.message = message;
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
//    public void setSend_time(Timestamp send_time) {
//        this.send_time = send_time;
//    }
//
//    public void setCommand(Command command) {
//        this.command = command;
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
//        hash = 97 * hash + Objects.hashCode(this.mt_his_id);
//        hash = 97 * hash + Objects.hashCode(this.transaction_id);
//        hash = 97 * hash + Objects.hashCode(this.msisdn);
//        hash = 97 * hash + Objects.hashCode(this.message);
//        hash = 97 * hash + Objects.hashCode(this.channel);
//        hash = 97 * hash + Objects.hashCode(this.receive_time);
//        hash = 97 * hash + Objects.hashCode(this.send_time);
//        hash = 97 * hash + Objects.hashCode(this.command);
//        hash = 97 * hash + Objects.hashCode(this.product);
//        hash = 97 * hash + Objects.hashCode(this.process_unit);
//        hash = 97 * hash + Objects.hashCode(this.IP_unit);
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
//        final Mt_Hist other = (Mt_Hist) obj;
//        if (!Objects.equals(this.transaction_id, other.transaction_id)) {
//            return false;
//        }
//        if (!Objects.equals(this.msisdn, other.msisdn)) {
//            return false;
//        }
//        if (!Objects.equals(this.message, other.message)) {
//            return false;
//        }
//        if (!Objects.equals(this.channel, other.channel)) {
//            return false;
//        }
//        if (!Objects.equals(this.process_unit, other.process_unit)) {
//            return false;
//        }
//        if (!Objects.equals(this.IP_unit, other.IP_unit)) {
//            return false;
//        }
//        if (!Objects.equals(this.mt_his_id, other.mt_his_id)) {
//            return false;
//        }
//        if (!Objects.equals(this.receive_time, other.receive_time)) {
//            return false;
//        }
//        if (!Objects.equals(this.send_time, other.send_time)) {
//            return false;
//        }
//        if (!Objects.equals(this.command, other.command)) {
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
//        return "Mt_Hist{" + "mt_his_id=" + mt_his_id + ", transaction_id=" + transaction_id + ", msisdn=" + msisdn + ", message=" + message + ", channel=" + channel + ", receive_time=" + receive_time + ", send_time=" + send_time + ", command=" + command + ", product=" + product + ", process_unit=" + process_unit + ", IP_unit=" + IP_unit + '}';
//    }
//
//   
//   
//}
