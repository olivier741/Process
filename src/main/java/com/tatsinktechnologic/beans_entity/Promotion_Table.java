///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tatsinktechnologic.beans_entity;
//
//import java.io.Serializable;
//import java.util.Objects;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.persistence.UniqueConstraint;
//
///**
// *
// * @author olivier.tatsinkou
// */
//@Entity
//@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"msisdn"})})
//public class Promotion_Table implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(unique = true, nullable = false)
//    private int promotion_table_id;
//    
//    private String msisdn;
//
//    public int getPromotion_table_id() {
//        return promotion_table_id;
//    }
//
//    public void setPromotion_table_id(int promotion_table_id) {
//        this.promotion_table_id = promotion_table_id;
//    }
//
//    public String getMsisdn() {
//        return msisdn;
//    }
//
//    public void setMsisdn(String msisdn) {
//        this.msisdn = msisdn;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 47 * hash + Objects.hashCode(this.promotion_table_id);
//        hash = 47 * hash + Objects.hashCode(this.msisdn);
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
//        final Promotion_Table other = (Promotion_Table) obj;
//        if (!Objects.equals(this.msisdn, other.msisdn)) {
//            return false;
//        }
//        if (!Objects.equals(this.promotion_table_id, other.promotion_table_id)) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "Promotion_Table{" + "promotion_table_id=" + promotion_table_id + ", msisdn=" + msisdn + '}';
//    }
//    
//    
//}
