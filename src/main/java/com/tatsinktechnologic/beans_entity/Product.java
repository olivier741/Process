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
//import java.util.HashSet;
//import java.util.Objects;
//import java.util.Set;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
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
//@Table(name = "product")
//@Check(constraints = "reg_fee >= 0 AND extend_fee >= 0")
//public class Product implements Serializable {
//            
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(unique = true, nullable = false)
//    private int product_id;
//
//    @Column(nullable = false, unique = true)
//    private String product_code;
//    
//    @Column(nullable = true)
//    private long reg_fee;
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
//    private boolean isextend = true;
//    
//    @Column(nullable = true)
//    private boolean isOveride_reg = true;
//    
//    @Column(nullable = true)
//    private boolean isNotify_extend=false;
//    
//    @Column(nullable = true)
//    private long extend_fee;
//    
//    @Column(nullable = true)
//    private String validity;          // D1 mean customer must have this offer for one Day, H5 mean customer must have this offer for 5 hours
//    
//    @Column(nullable = true)
//    private String pending_duration;  // D30 mean customer pending 30 Day on this offert, he is cancel (system will not try to extend) 
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
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "promotion_id", nullable = true)
//    private Promotion promotion;
//    
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "service_id", nullable = true)
//    private Service service;
//   
//    @OneToMany(mappedBy = "product")
//    private Set<Action> listAction = new HashSet<>();
//    
//    @OneToMany(mappedBy = "product")
//    private Set<Register> listRegister= new HashSet<>();
//    
//    @OneToMany(mappedBy = "product")
//    private Set<Charge_Hist_Success> listCharge_Hist= new HashSet<>();
//    
//    @OneToMany(mappedBy = "product")
//    private Set<Mo_Hist> listMo_Hist= new HashSet<>();
//    
//    @OneToMany(mappedBy = "product")
//    private Set<Mt_Hist> listMt_Hist= new HashSet<>();
//
//    public int getProduct_id() {
//        return product_id;
//    }
//
//    public void setProduct_id(int product_id) {
//        this.product_id = product_id;
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
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
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
//    public Promotion getPromotion() {
//        return promotion;
//    }
//
//    public void setPromotion(Promotion promotion) {
//        this.promotion = promotion;
//    }
//
//    public Service getService() {
//        return service;
//    }
//
//    public void setService(Service service) {
//        this.service = service;
//    }
//
//    public Set<Action> getListAction() {
//        return listAction;
//    }
//
//    public void setListAction(Set<Action> listAction) {
//        this.listAction = listAction;
//    }
//
//    public Set<Register> getListRegister() {
//        return listRegister;
//    }
//
//    public void setListRegister(Set<Register> listRegister) {
//        this.listRegister = listRegister;
//    }
//
//    public Set<Charge_Hist_Success> getListCharge_Hist() {
//        return listCharge_Hist;
//    }
//
//    public void setListCharge_Hist(Set<Charge_Hist_Success> listCharge_Hist) {
//        this.listCharge_Hist = listCharge_Hist;
//    }
//
//    public Set<Mo_Hist> getListMo_Hist() {
//        return listMo_Hist;
//    }
//
//    public void setListMo_Hist(Set<Mo_Hist> listMo_Hist) {
//        this.listMo_Hist = listMo_Hist;
//    }
//
//    public Set<Mt_Hist> getListMt_Hist() {
//        return listMt_Hist;
//    }
//
//    public void setListMt_Hist(Set<Mt_Hist> listMt_Hist) {
//        this.listMt_Hist = listMt_Hist;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 41 * hash + this.product_id;
//        hash = 41 * hash + Objects.hashCode(this.product_code);
//        hash = 41 * hash + (int) (this.reg_fee ^ (this.reg_fee >>> 32));
//        hash = 41 * hash + Objects.hashCode(this.restrict_product);
//        hash = 41 * hash + Objects.hashCode(this.register_day);
//        hash = 41 * hash + Objects.hashCode(this.start_date);
//        hash = 41 * hash + Objects.hashCode(this.end_date);
//        hash = 41 * hash + Objects.hashCode(this.start_reg_time);
//        hash = 41 * hash + Objects.hashCode(this.end_reg_time);
//        hash = 41 * hash + (this.isextend ? 1 : 0);
//        hash = 41 * hash + (this.isOveride_reg ? 1 : 0);
//        hash = 41 * hash + (this.isNotify_extend ? 1 : 0);
//        hash = 41 * hash + (int) (this.extend_fee ^ (this.extend_fee >>> 32));
//        hash = 41 * hash + Objects.hashCode(this.validity);
//        hash = 41 * hash + Objects.hashCode(this.pending_duration);
//        hash = 41 * hash + Objects.hashCode(this.description);
//        hash = 41 * hash + Objects.hashCode(this.create_time);
//        hash = 41 * hash + Objects.hashCode(this.update_time);
//        hash = 41 * hash + Objects.hashCode(this.promotion);
//        hash = 41 * hash + Objects.hashCode(this.service);
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
//        final Product other = (Product) obj;
//        if (this.product_id != other.product_id) {
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
//        if (!Objects.equals(this.description, other.description)) {
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
//        if (!Objects.equals(this.create_time, other.create_time)) {
//            return false;
//        }
//        if (!Objects.equals(this.update_time, other.update_time)) {
//            return false;
//        }
//        if (!Objects.equals(this.promotion, other.promotion)) {
//            return false;
//        }
//        if (!Objects.equals(this.service, other.service)) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "Product{" + "product_id=" + product_id + ", product_code=" + product_code + ", reg_fee=" + reg_fee + ", restrict_product=" + restrict_product + ", register_day=" + register_day + ", start_date=" + start_date + ", end_date=" + end_date + ", start_reg_time=" + start_reg_time + ", end_reg_time=" + end_reg_time + ", isextend=" + isextend + ", isOveride_reg=" + isOveride_reg + ", isNotify_extend=" + isNotify_extend + ", extend_fee=" + extend_fee + ", validity=" + validity + ", pending_duration=" + pending_duration + ", description=" + description + ", create_time=" + create_time + ", update_time=" + update_time + ", promotion=" + promotion + ", service=" + service + '}';
//    }
//    
//
//}
