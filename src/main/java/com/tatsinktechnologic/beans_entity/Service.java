///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tatsinktechnologic.beans_entity;
//
//
//import java.io.Serializable;
//import java.util.HashSet;
//import java.util.Set;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//import java.sql.Timestamp;
//import java.util.Objects;
//import javax.persistence.Column;
//import javax.persistence.Table;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//
///**
// *
// * @author olivier
// */
//@Entity
//@Table(name = "service")
//public class Service implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(unique = true, nullable = false)
//    private int service_id;
//
//    @Column(nullable = false, unique = true)
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
//    @CreationTimestamp
//    private Timestamp create_time;
//    
//    @Column(nullable = true)
//    @UpdateTimestamp
//    private Timestamp update_time;
//    
//    
//    @OneToMany(mappedBy = "service")
//    private Set<Product> listProduct = new HashSet<>();
//    
//    @OneToMany(mappedBy = "service")
//    private Set<Alias> listAlias = new HashSet<>();
//
//    public int getService_id() {
//        return service_id;
//    }
//
//    public String getService_name() {
//        return service_name;
//    }
//
//    public String getReceive_channel() {
//        return receive_channel;
//    }
//
//    public String getSend_channel() {
//        return send_channel;
//    }
//
//   
//    public String getService_provider() {
//        return service_provider;
//    }
//
//    public String getService_description() {
//        return service_description;
//    }
//
//    public Timestamp getCreate_time() {
//        return create_time;
//    }
//
//    public Set<Product> getListProduct() {
//        return listProduct;
//    }
//
//    public void setService_id(int service_id) {
//        this.service_id = service_id;
//    }
//
//    public void setService_name(String service_name) {
//        this.service_name = service_name;
//    }
//
//    public void setReceive_channel(String receive_channel) {
//        this.receive_channel = receive_channel;
//    }
//
//    public void setSend_channel(String send_channel) {
//        this.send_channel = send_channel;
//    }
//
//   
//    public void setService_provider(String service_provider) {
//        this.service_provider = service_provider;
//    }
//
//    public void setService_description(String service_description) {
//        this.service_description = service_description;
//    }
//
//    public void setCreate_time(Timestamp create_time) {
//        this.create_time = create_time;
//    }
//
//    public void setListProduct(Set<Product> listProduct) {
//        this.listProduct = listProduct;
//    }
//
//    public Set<Alias> getListAlias() {
//        return listAlias;
//    }
//
//    public void setListAlias(Set<Alias> listAlias) {
//        this.listAlias = listAlias;
//    }
//    
//    
//
//    public Timestamp getUpdate_time() {
//        return update_time;
//    }
//
//    public void setUpdate_time(Timestamp update_time) {
//        this.update_time = update_time;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 11 * hash + this.service_id;
//        hash = 11 * hash + Objects.hashCode(this.service_name);
//        hash = 11 * hash + Objects.hashCode(this.receive_channel);
//        hash = 11 * hash + Objects.hashCode(this.send_channel);
//        hash = 11 * hash + Objects.hashCode(this.service_provider);
//        hash = 11 * hash + Objects.hashCode(this.service_description);
//        hash = 11 * hash + Objects.hashCode(this.create_time);
//        hash = 11 * hash + Objects.hashCode(this.update_time);
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
//        final Service other = (Service) obj;
//        if (this.service_id != other.service_id) {
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
//        if (!Objects.equals(this.create_time, other.create_time)) {
//            return false;
//        }
//        if (!Objects.equals(this.update_time, other.update_time)) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "Service{" + "service_id=" + service_id + ", service_name=" + service_name + ", receive_channel=" + receive_channel + ", send_channel=" + send_channel + ", service_provider=" + service_provider + ", service_description=" + service_description + ", create_time=" + create_time + ", update_time=" + update_time + '}';
//    }
//    
//
//}
