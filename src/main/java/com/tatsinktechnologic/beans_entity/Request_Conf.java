///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tatsinktechnologic.beans_entity;
//
//import java.io.Serializable;
//import java.sql.Time;
//import java.sql.Timestamp;
//import java.util.Objects;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.Id;
//import org.hibernate.annotations.Immutable;
//import org.hibernate.annotations.Subselect;
//import org.hibernate.annotations.Synchronize;
//
///**
// *
// * @author olivier.tatsinkou
// */
//@Entity
//@Subselect(
//"   SELECT cmd.command_id,  \n"+
//"          cmd.command_name,  \n" +
//"          cmd.command_code,  \n" +
//"	   cmd.split_separator, \n" +
//        
//"	   ac.action_name,  \n" +
//"	   ac.action_type,  \n" +
//"	   ac.description action_description,    \n" +
//        
//"	   prod.product_code, \n" +
//"	   prod.reg_fee,  \n" +
//"	   prod.restrict_product, \n" +
//"	   prod.register_day, \n" +
//"	   prod.start_date,   \n" +     
//"	   prod.end_date, \n" +
//"	   prod.start_reg_time,   \n" +        
//"	   prod.end_reg_time, \n" +
//"	   prod.isextend, \n" +
//"	   prod.isOveride_reg, \n" +
//"	   prod.isNotify_extend, \n" +
//"	   prod.extend_fee,   \n" +
//"	   prod.validity, \n" +        
//"	   prod.pending_duration, \n" +
//
//"	   ser.service_name, \n" +
//"          ser.receive_channel, \n" +
//"          ser.send_channel, \n" +
//"          ser.service_provider, \n" +
//"          ser.service_description,\n" +
//        
//"	   pr.param_name,   \n" +
//"	   pr.param_pattern,    \n" +
//"	   pr.param_length,   \n" +
//"	   pr.description  paramDescription \n" +
//"   FROM command cmd   \n" +
//"   LEFT JOIN parameter pr ON cmd.parameter_id = pr.parameter_id \n" +
//"   LEFT JOIN action ac ON cmd.action_id = ac.action_id \n" +
//"   LEFT JOIN product prod ON ac.product_id = prod.product_id \n" +
//"   LEFT JOIN service ser ON prod.service_id = ser.service_id ")
//@Synchronize({"Command", "Parameter","Action","Product","Service"})
//@Immutable
//public class Request_Conf implements Serializable {
//    @Id
//    private int command_id;
//    private String command_name;
//    private String command_code;
//    
//    @Column(nullable = true)
//    private String split_separator;
//    
//    @Column(nullable = true)
//    private String action_name;
//    
//    @Column(nullable = true)
//    @Enumerated(EnumType.STRING)
//    private Action_Type action_type;
//    
//    @Column(nullable = true)
//    private String action_description;
//    
//    @Column(nullable = true)
//    private String product_code;
//    
//    @Column(nullable = true)
//    private Long reg_fee;
//    
//    @Column(nullable = true)
//    private String restrict_product;  // list of restric product separate by | (e.g : CAN1|CAN2)
//    
//    @Column(nullable = true)
//    private String register_day;      // 'Day allow register: (e.g 1|2|3) 0-Sunday, 1-Monday, 2-Tuesday, 3-Wednesday, ... not information mean registration every day'
//    
//    @Column(nullable = true)
//    private Timestamp start_date;     //  2019-04-16 23:00:01-07:00:00  this promotion will launch  the 2019-04-16 at 11PM 
//    
//    @Column(nullable = true)
//    private Timestamp end_date;       //  2050-04-16 23:00:01-07:00:00  this promotion will end  the 2050-04-16 at 11PM 
//    
//    @Column(nullable = true)
//    private Time start_reg_time;      //  23:00:01  star time in one day from when customer allow to register
//    
//    @Column(nullable = true)
//    private Time end_reg_time;        //  07:00:00  end time in one day from when customer not allow to register   
//   
//    @Column(nullable = true)
//    private Boolean isextend = true;
//    
//    @Column(nullable = true)
//    private Boolean isOveride_reg = true;
//    
//    @Column(nullable = true)
//    private Boolean isNotify_extend=false;
//    
//    @Column(nullable = true)
//    private Long extend_fee;
//    
//    @Column(nullable = true)
//    private String validity;          // D1 mean customer must have this offer for one Day, H5 mean customer must have this offer for 5 hours
//    
//    @Column(nullable = true)
//    private String pending_duration;
//    
//    @Column(nullable = true)
//    private String service_name;
//    
//    @Column(nullable = true)
//    private String receive_channel;
//    
//    @Column(nullable = true)
//    private String send_channel;
//    
//    @Column(nullable = true)
//    private String service_provider;
//    
//    @Column(nullable = true)
//    private String service_description;
//    
//    @Column(nullable = true) 
//    private String param_name;
//    
//    @Column(nullable = true)
//    private Integer param_length;
//    
//    @Column(nullable = true)
//    private String param_pattern;
//    
//    @Column(nullable = true)
//    private String paramDescription; 
//
//    public int getCommand_id() {
//        return command_id;
//    }
//
//    public void setCommand_id(int command_id) {
//        this.command_id = command_id;
//    }
//
//    public String getCommand_name() {
//        return command_name;
//    }
//
//    public void setCommand_name(String command_name) {
//        this.command_name = command_name;
//    }
//
//    public String getCommand_code() {
//        return command_code;
//    }
//
//    public void setCommand_code(String command_code) {
//        this.command_code = command_code;
//    }
//
//    public String getSplit_separator() {
//        return split_separator;
//    }
//
//    public void setSplit_separator(String split_separator) {
//        this.split_separator = split_separator;
//    }
//
//    public String getAction_name() {
//        return action_name;
//    }
//
//    public void setAction_name(String action_name) {
//        this.action_name = action_name;
//    }
//
//    public Action_Type getAction_type() {
//        return action_type;
//    }
//
//    public void setAction_type(Action_Type action_type) {
//        this.action_type = action_type;
//    }
//
//    public String getAction_description() {
//        return action_description;
//    }
//
//    public void setAction_description(String action_description) {
//        this.action_description = action_description;
//    }
//
//    public String getProduct_code() {
//        return product_code;
//    }
//
//    public void setProduct_code(String product_code) {
//        this.product_code = product_code;
//    }
//
//    public long getReg_fee() {
//        return reg_fee;
//    }
//
//    public void setReg_fee(long reg_fee) {
//        this.reg_fee = reg_fee;
//    }
//
//    public String getRestrict_product() {
//        return restrict_product;
//    }
//
//    public void setRestrict_product(String restrict_product) {
//        this.restrict_product = restrict_product;
//    }
//
//    public String getRegister_day() {
//        return register_day;
//    }
//
//    public void setRegister_day(String register_day) {
//        this.register_day = register_day;
//    }
//
//    public Timestamp getStart_date() {
//        return start_date;
//    }
//
//    public void setStart_date(Timestamp start_date) {
//        this.start_date = start_date;
//    }
//
//    public Timestamp getEnd_date() {
//        return end_date;
//    }
//
//    public void setEnd_date(Timestamp end_date) {
//        this.end_date = end_date;
//    }
//
//    public Time getStart_reg_time() {
//        return start_reg_time;
//    }
//
//    public void setStart_reg_time(Time start_reg_time) {
//        this.start_reg_time = start_reg_time;
//    }
//
//    public Time getEnd_reg_time() {
//        return end_reg_time;
//    }
//
//    public void setEnd_reg_time(Time end_reg_time) {
//        this.end_reg_time = end_reg_time;
//    }
//
//    public boolean isIsextend() {
//        return isextend;
//    }
//
//    public void setIsextend(boolean isextend) {
//        this.isextend = isextend;
//    }
//
//    public boolean isIsOveride_reg() {
//        return isOveride_reg;
//    }
//
//    public void setIsOveride_reg(boolean isOveride_reg) {
//        this.isOveride_reg = isOveride_reg;
//    }
//
//    public boolean isIsNotify_extend() {
//        return isNotify_extend;
//    }
//
//    public void setIsNotify_extend(boolean isNotify_extend) {
//        this.isNotify_extend = isNotify_extend;
//    }
//
//    public long getExtend_fee() {
//        return extend_fee;
//    }
//
//    public void setExtend_fee(long extend_fee) {
//        this.extend_fee = extend_fee;
//    }
//
//    public String getValidity() {
//        return validity;
//    }
//
//    public void setValidity(String validity) {
//        this.validity = validity;
//    }
//
//    public String getPending_duration() {
//        return pending_duration;
//    }
//
//    public void setPending_duration(String pending_duration) {
//        this.pending_duration = pending_duration;
//    }
//
//    public String getService_name() {
//        return service_name;
//    }
//
//    public void setService_name(String service_name) {
//        this.service_name = service_name;
//    }
//
//    public String getReceive_channel() {
//        return receive_channel;
//    }
//
//    public void setReceive_channel(String receive_channel) {
//        this.receive_channel = receive_channel;
//    }
//
//    public String getSend_channel() {
//        return send_channel;
//    }
//
//    public void setSend_channel(String send_channel) {
//        this.send_channel = send_channel;
//    }
//
//    public String getService_provider() {
//        return service_provider;
//    }
//
//    public void setService_provider(String service_provider) {
//        this.service_provider = service_provider;
//    }
//
//    public String getService_description() {
//        return service_description;
//    }
//
//    public void setService_description(String service_description) {
//        this.service_description = service_description;
//    }
//
//    public String getParam_name() {
//        return param_name;
//    }
//
//    public void setParam_name(String param_name) {
//        this.param_name = param_name;
//    }
//
//    public Integer getParam_length() {
//        return param_length;
//    }
//
//    public void setParam_length(Integer param_length) {
//        this.param_length = param_length;
//    }
//
//    public String getParam_pattern() {
//        return param_pattern;
//    }
//
//    public void setParam_pattern(String param_pattern) {
//        this.param_pattern = param_pattern;
//    }
//
//    public String getParamDescription() {
//        return paramDescription;
//    }
//
//    public void setParamDescription(String paramDescription) {
//        this.paramDescription = paramDescription;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 5;
//        hash = 13 * hash + this.command_id;
//        hash = 13 * hash + Objects.hashCode(this.command_name);
//        hash = 13 * hash + Objects.hashCode(this.command_code);
//        hash = 13 * hash + Objects.hashCode(this.split_separator);
//        hash = 13 * hash + Objects.hashCode(this.action_name);
//        hash = 13 * hash + Objects.hashCode(this.action_type);
//        hash = 13 * hash + Objects.hashCode(this.action_description);
//        hash = 13 * hash + Objects.hashCode(this.product_code);
//        hash = 13 * hash + (int) (this.reg_fee ^ (this.reg_fee >>> 32));
//        hash = 13 * hash + Objects.hashCode(this.restrict_product);
//        hash = 13 * hash + Objects.hashCode(this.register_day);
//        hash = 13 * hash + Objects.hashCode(this.start_date);
//        hash = 13 * hash + Objects.hashCode(this.end_date);
//        hash = 13 * hash + Objects.hashCode(this.start_reg_time);
//        hash = 13 * hash + Objects.hashCode(this.end_reg_time);
//        hash = 13 * hash + (this.isextend ? 1 : 0);
//        hash = 13 * hash + (this.isOveride_reg ? 1 : 0);
//        hash = 13 * hash + (this.isNotify_extend ? 1 : 0);
//        hash = 13 * hash + (int) (this.extend_fee ^ (this.extend_fee >>> 32));
//        hash = 13 * hash + Objects.hashCode(this.validity);
//        hash = 13 * hash + Objects.hashCode(this.pending_duration);
//        hash = 13 * hash + Objects.hashCode(this.service_name);
//        hash = 13 * hash + Objects.hashCode(this.receive_channel);
//        hash = 13 * hash + Objects.hashCode(this.send_channel);
//        hash = 13 * hash + Objects.hashCode(this.service_provider);
//        hash = 13 * hash + Objects.hashCode(this.service_description);
//        hash = 13 * hash + Objects.hashCode(this.param_name);
//        hash = 13 * hash + Objects.hashCode(this.param_length);
//        hash = 13 * hash + Objects.hashCode(this.param_pattern);
//        hash = 13 * hash + Objects.hashCode(this.paramDescription);
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
//        final Request_Conf other = (Request_Conf) obj;
//        if (this.command_id != other.command_id) {
//            return false;
//        }
//        if (this.reg_fee != other.reg_fee) {
//            return false;
//        }
//        if (this.isextend != other.isextend) {
//            return false;
//        }
//        if (this.isOveride_reg != other.isOveride_reg) {
//            return false;
//        }
//        if (this.isNotify_extend != other.isNotify_extend) {
//            return false;
//        }
//        if (this.extend_fee != other.extend_fee) {
//            return false;
//        }
//        if (!Objects.equals(this.command_name, other.command_name)) {
//            return false;
//        }
//        if (!Objects.equals(this.command_code, other.command_code)) {
//            return false;
//        }
//        if (!Objects.equals(this.split_separator, other.split_separator)) {
//            return false;
//        }
//        if (!Objects.equals(this.action_name, other.action_name)) {
//            return false;
//        }
//        if (!Objects.equals(this.action_description, other.action_description)) {
//            return false;
//        }
//        if (!Objects.equals(this.product_code, other.product_code)) {
//            return false;
//        }
//        if (!Objects.equals(this.restrict_product, other.restrict_product)) {
//            return false;
//        }
//        if (!Objects.equals(this.register_day, other.register_day)) {
//            return false;
//        }
//        if (!Objects.equals(this.validity, other.validity)) {
//            return false;
//        }
//        if (!Objects.equals(this.pending_duration, other.pending_duration)) {
//            return false;
//        }
//        if (!Objects.equals(this.service_name, other.service_name)) {
//            return false;
//        }
//        if (!Objects.equals(this.receive_channel, other.receive_channel)) {
//            return false;
//        }
//        if (!Objects.equals(this.send_channel, other.send_channel)) {
//            return false;
//        }
//        if (!Objects.equals(this.service_provider, other.service_provider)) {
//            return false;
//        }
//        if (!Objects.equals(this.service_description, other.service_description)) {
//            return false;
//        }
//        if (!Objects.equals(this.param_name, other.param_name)) {
//            return false;
//        }
//        if (!Objects.equals(this.param_pattern, other.param_pattern)) {
//            return false;
//        }
//        if (!Objects.equals(this.paramDescription, other.paramDescription)) {
//            return false;
//        }
//        if (this.action_type != other.action_type) {
//            return false;
//        }
//        if (!Objects.equals(this.start_date, other.start_date)) {
//            return false;
//        }
//        if (!Objects.equals(this.end_date, other.end_date)) {
//            return false;
//        }
//        if (!Objects.equals(this.start_reg_time, other.start_reg_time)) {
//            return false;
//        }
//        if (!Objects.equals(this.end_reg_time, other.end_reg_time)) {
//            return false;
//        }
//        if (!Objects.equals(this.param_length, other.param_length)) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "Request_Conf{" + "command_id=" + command_id + ", command_name=" + command_name + ", command_code=" + command_code + ", split_separator=" + split_separator + ", action_name=" + action_name + ", action_type=" + action_type + ", action_description=" + action_description + ", product_code=" + product_code + ", reg_fee=" + reg_fee + ", restrict_product=" + restrict_product + ", register_day=" + register_day + ", start_date=" + start_date + ", end_date=" + end_date + ", start_reg_time=" + start_reg_time + ", end_reg_time=" + end_reg_time + ", isextend=" + isextend + ", isOveride_reg=" + isOveride_reg + ", isNotify_extend=" + isNotify_extend + ", extend_fee=" + extend_fee + ", validity=" + validity + ", pending_duration=" + pending_duration + ", service_name=" + service_name + ", receive_channel=" + receive_channel + ", send_channel=" + send_channel + ", service_provider=" + service_provider + ", service_description=" + service_description + ", param_name=" + param_name + ", param_length=" + param_length + ", param_pattern=" + param_pattern + ", paramDescription=" + paramDescription + '}';
//    }
//
//    
//  
//}
