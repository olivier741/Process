///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tatsinktechnologic.beans_entity;
//
//import java.io.Serializable;
//import java.sql.Timestamp;
//import java.util.HashSet;
//import java.util.Objects;
//import java.util.Set;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
///**
// *
// * @author olivier
// */
//@Entity
//@Table(name = "parameter")
//public class Parameter implements Serializable {
//    
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(unique = true, nullable = false)
//    private int parameter_id;
//        
//    @Column(nullable = false, unique = true)
//    private String param_name;
//    
//    @Column(nullable = true)
//    private int param_length;
//    
//    @Column(nullable = true)
//    private String param_pattern;
//    
//     @Column(nullable = true)
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
//    
//    @OneToMany(mappedBy = "parameter")
//    private Set<Command> listCommand = new HashSet<>();
//
//    public int getParameter_id() {
//        return parameter_id;
//    }
//
//    public void setParameter_id(int parameter_id) {
//        this.parameter_id = parameter_id;
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
//    public int getParam_length() {
//        return param_length;
//    }
//
//    public void setParam_length(int param_length) {
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
//    public Set<Command> getListCommand() {
//        return listCommand;
//    }
//
//    public void setListCommand(Set<Command> listCommand) {
//        this.listCommand = listCommand;
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
//    
//
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 17 * hash + this.parameter_id;
//        hash = 17 * hash + Objects.hashCode(this.param_name);
//        hash = 17 * hash + this.param_length;
//        hash = 17 * hash + Objects.hashCode(this.param_pattern);
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
//        final Parameter other = (Parameter) obj;
//        if (this.parameter_id != other.parameter_id) {
//            return false;
//        }
//        if (this.param_length != other.param_length) {
//            return false;
//        }
//        if (!Objects.equals(this.param_name, other.param_name)) {
//            return false;
//        }
//        if (!Objects.equals(this.param_pattern, other.param_pattern)) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "Parameter{" + "parameter_id=" + parameter_id + ", param_name=" + param_name + ", param_length=" + param_length + ", param_pattern=" + param_pattern + '}';
//    }
//
//   
//
//   
//}
