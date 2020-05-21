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
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//import javax.persistence.UniqueConstraint;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//
///**
// *
// * @author olivier
// */
//@Entity
//@Table(name = "command",uniqueConstraints={@UniqueConstraint(columnNames = {"command_code","action_id"})})
//public class Command implements Serializable {
//    
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(unique = true, nullable = false)
//    private int command_id;
//    
//    @Column(nullable = false, unique = true)
//    private String command_name;
//    
//    @Column(nullable = false, unique = false)
//    private String command_code;
//    
//    @Column(nullable = true)
//    private String split_separator;
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
//    
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "action_id", nullable = true)
//    private Action action;
//    
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "parameter_id", nullable = true)
//    private Parameter parameter;
//    
//    @OneToMany(mappedBy = "command")
//    private Set<Mo_Hist> listMo_Hist = new HashSet<>();
//    
//    @OneToMany(mappedBy = "command")
//    private Set<Mt_Hist> listMt_Hist = new HashSet<>();
//    
//    @OneToMany(mappedBy = "command")
//    private Set<Notification_Conf> listConfig = new HashSet<>();
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
//    public Set<Notification_Conf> getListConfig() {
//        return listConfig;
//    }
//
//    public void setListConfig(Set<Notification_Conf> listConfig) {
//        this.listConfig = listConfig;
//    }
//    
//    
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
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public Action getAction() {
//        return action;
//    }
//
//    public void setAction(Action action) {
//        this.action = action;
//    }
//
//    public Parameter getParameter() {
//        return parameter;
//    }
//
//    public void setParameter(Parameter parameter) {
//        this.parameter = parameter;
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
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 59 * hash + this.command_id;
//        hash = 59 * hash + Objects.hashCode(this.command_name);
//        hash = 59 * hash + Objects.hashCode(this.command_code);
//        hash = 59 * hash + Objects.hashCode(this.split_separator);
//        hash = 59 * hash + Objects.hashCode(this.description);
//        hash = 59 * hash + Objects.hashCode(this.create_time);
//        hash = 59 * hash + Objects.hashCode(this.update_time);
//        hash = 59 * hash + Objects.hashCode(this.action);
//        hash = 59 * hash + Objects.hashCode(this.parameter);
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
//        final Command other = (Command) obj;
//        if (this.command_id != other.command_id) {
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
//        if (!Objects.equals(this.description, other.description)) {
//            return false;
//        }
//        if (!Objects.equals(this.create_time, other.create_time)) {
//            return false;
//        }
//        if (!Objects.equals(this.update_time, other.update_time)) {
//            return false;
//        }
//        if (!Objects.equals(this.action, other.action)) {
//            return false;
//        }
//        if (!Objects.equals(this.parameter, other.parameter)) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "Command{" + "command_id=" + command_id + ", command_name=" + command_name + ", command_code=" + command_code + ", split_separator=" + split_separator + ", description=" + description + ", create_time=" + create_time + ", update_time=" + update_time + ", action=" + action + ", parameter=" + parameter + '}';
//    }
//    
//    
//
//  
//   
//}
