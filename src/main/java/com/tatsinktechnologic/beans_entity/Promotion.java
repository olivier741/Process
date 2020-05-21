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
//import javax.persistence.SequenceGenerator;
//import java.sql.Date;
//import java.sql.Timestamp;
//import java.util.HashSet;
//import java.util.Objects;
//import java.util.Set;
//import javax.persistence.Column;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.FetchType;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//import javax.persistence.Transient;
//import javax.persistence.UniqueConstraint;
//import org.apache.log4j.Logger;
//import org.hibernate.annotations.Check;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//
///**
// *
// * @author olivier
// */
//@Entity
//@Table(name = "promotion")
//@Check(constraints = "promotion_reg_fee >= 0 and promotion_ext_fee >=0")
//public class Promotion implements Serializable {
//
//        
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(unique = true, nullable = false)
//    private int promotion_id;
//    
//    @Column(nullable = false, unique = true)
//    private String promotion_name;
//    
//    @Enumerated(EnumType.STRING)
//    private Promo_Filter promotion_filter;
//        
//    @Column(nullable = true)
//    private String msisdn_table;
//    
//    @Column(nullable = true)
//    private String msisdn_regex;
//    
//    @Column(nullable = true)
//    private Timestamp start_time;
//    
//    @Column(nullable = true)
//    private Timestamp end_time;
//    
//    @Enumerated(EnumType.STRING)
//    private Reduction_Type reduction_mode;
//
//    @Column(nullable = true)
//    private long promotion_reg_fee;
//    
//    @Column(nullable = true)
//    private long percentage_reg;
//    
//    @Column(nullable = true)
//    private boolean isExtend = false;
//    
//    @Column(nullable = true)
//    private long promotion_ext_fee;
//    
//    @Column(nullable = true)
//    private long percentage_ext;
//    
//    @Column(nullable = true)
//    private String description;
//    
//    @Column(nullable = true)
//    @CreationTimestamp
//    private Timestamp create_time;
//    
//    @Column(nullable = true)
//    @UpdateTimestamp
//    private Timestamp update_time;
//    
//    @OneToMany(mappedBy = "promotion")
//    protected Set<Product> listProduct = new HashSet<>();
//
//    public int getPromotion_id() {
//        return promotion_id;
//    }
//
//    public String getPromotion_name() {
//        return promotion_name;
//    }
//
//    public String getMsisdn_table() {
//        return msisdn_table;
//    }
//
//    public String getMsisdn_regex() {
//        return msisdn_regex;
//    }
//
//    public Timestamp getStart_time() {
//        return start_time;
//    }
//
//    public Timestamp getEnd_time() {
//        return end_time;
//    }
//
//    public long getPromotion_reg_fee() {
//        return promotion_reg_fee;
//    }
//
//    public long getPercentage_reg() {
//        return percentage_reg;
//    }
//
//    public boolean isIsExtend() {
//        return isExtend;
//    }
//
//    public long getPromotion_ext_fee() {
//        return promotion_ext_fee;
//    }
//
//    public long getPercentage_ext() {
//        return percentage_ext;
//    }
//
//    public Reduction_Type getReduction_mode() {
//        return reduction_mode;
//    }
//
//    public Promo_Filter getPromotion_filter() {
//        return promotion_filter;
//    }
//
//    public Set<Product> getListProduct() {
//        return listProduct;
//    }
//
//    public void setPromotion_id(int promotion_id) {
//        this.promotion_id = promotion_id;
//    }
//
//    public void setPromotion_name(String promotion_name) {
//        this.promotion_name = promotion_name;
//    }
//
//    public void setMsisdn_table(String msisdn_table) {
//        this.msisdn_table = msisdn_table;
//    }
//
//    public void setMsisdn_regex(String msisdn_regex) {
//        this.msisdn_regex = msisdn_regex;
//    }
//
//    public void setStart_time(Timestamp start_time) {
//        this.start_time = start_time;
//    }
//
//    public void setEnd_time(Timestamp end_time) {
//        this.end_time = end_time;
//    }
//
//    public void setPromotion_reg_fee(long promotion_reg_fee) {
//        this.promotion_reg_fee = promotion_reg_fee;
//    }
//
//    public void setPercentage_reg(long percentage_reg) {
//        this.percentage_reg = percentage_reg;
//    }
//
//    public void setIsExtend(boolean isExtend) {
//        this.isExtend = isExtend;
//    }
//
//    public void setPromotion_ext_fee(long promotion_ext_fee) {
//        this.promotion_ext_fee = promotion_ext_fee;
//    }
//
//    public void setPercentage_ext(long percentage_ext) {
//        this.percentage_ext = percentage_ext;
//    }
//
//    public void setReduction_mode(Reduction_Type reduction_mode) {
//        this.reduction_mode = reduction_mode;
//    }
//
//    public void setPromotion_filter(Promo_Filter promotion_filter) {
//        this.promotion_filter = promotion_filter;
//    }
//
//    public void setListProduct(Set<Product> listProduct) {
//        this.listProduct = listProduct;
//    }
//
//    public Timestamp getCreate_time() {
//        return create_time;
//    }
//
//    public void setCreate_time(Timestamp create_time) {
//        this.create_time = create_time;
//    }
//
//    public Timestamp getUpdate_time() {
//        return update_time;
//    }
//
//    public void setUpdate_time(Timestamp update_time) {
//        this.update_time = update_time;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//    
//    
//    
//
//    @Override
//    public int hashCode() {
//        int hash = 3;
//        hash = 23 * hash + this.promotion_id;
//        hash = 23 * hash + Objects.hashCode(this.promotion_name);
//        hash = 23 * hash + Objects.hashCode(this.msisdn_table);
//        hash = 23 * hash + Objects.hashCode(this.msisdn_regex);
//        hash = 23 * hash + Objects.hashCode(this.start_time);
//        hash = 23 * hash + Objects.hashCode(this.end_time);
//        hash = 23 * hash + (int) (this.promotion_reg_fee ^ (this.promotion_reg_fee >>> 32));
//        hash = 23 * hash + (int) (this.percentage_reg ^ (this.percentage_reg >>> 32));
//        hash = 23 * hash + (this.isExtend ? 1 : 0);
//        hash = 23 * hash + (int) (this.promotion_ext_fee ^ (this.promotion_ext_fee >>> 32));
//        hash = 23 * hash + (int) (this.percentage_ext ^ (this.percentage_ext >>> 32));
//        hash = 23 * hash + Objects.hashCode(this.reduction_mode);
//        hash = 23 * hash + Objects.hashCode(this.promotion_filter);
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
//        final Promotion other = (Promotion) obj;
//        if (this.promotion_id != other.promotion_id) {
//            return false;
//        }
//        if (this.promotion_reg_fee != other.promotion_reg_fee) {
//            return false;
//        }
//        if (this.percentage_reg != other.percentage_reg) {
//            return false;
//        }
//        if (this.isExtend != other.isExtend) {
//            return false;
//        }
//        if (this.promotion_ext_fee != other.promotion_ext_fee) {
//            return false;
//        }
//        if (this.percentage_ext != other.percentage_ext) {
//            return false;
//        }
//        if (!Objects.equals(this.promotion_name, other.promotion_name)) {
//            return false;
//        }
//        if (!Objects.equals(this.msisdn_table, other.msisdn_table)) {
//            return false;
//        }
//        if (!Objects.equals(this.msisdn_regex, other.msisdn_regex)) {
//            return false;
//        }
//        if (!Objects.equals(this.start_time, other.start_time)) {
//            return false;
//        }
//        if (!Objects.equals(this.end_time, other.end_time)) {
//            return false;
//        }
//        if (this.reduction_mode != other.reduction_mode) {
//            return false;
//        }
//        if (this.promotion_filter != other.promotion_filter) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "Promotion{" + "promotion_id=" + promotion_id + ", promotion_name=" + promotion_name + ", msisdn_table=" + msisdn_table + ", msisdn_regex=" + msisdn_regex + ", start_time=" + start_time + ", end_time=" + end_time + ", promotion_reg_fee=" + promotion_reg_fee + ", percentage_reg=" + percentage_reg + ", isExtend=" + isExtend + ", promotion_ext_fee=" + promotion_ext_fee + ", percentage_ext=" + percentage_ext + ", reduction_mode=" + reduction_mode + ", promotion_filter=" + promotion_filter + '}';
//    }
//
// 
//}
