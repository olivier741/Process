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
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
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
///**
// *
// * @author olivier
// */
//@Entity
//@Table(name = "action",uniqueConstraints={@UniqueConstraint(columnNames = {"action_type","product_id"})})
//public class Action implements Serializable {
//    
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(unique = true, nullable = false)
//    private int action_id;
//
//    @Column(nullable = false, unique = true)
//    private String action_name;
//    
//    @Enumerated(EnumType.STRING)
//    private Action_Type action_type;
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
//    @JoinColumn(name = "product_id", nullable = true)
//    private Product product;
//    
//    @OneToMany(mappedBy = "action")
//    private Set<Command> listCommand = new HashSet<>();
//    
//
//    public int getAction_id() {
//        return action_id;
//    }
//
//    public String getAction_name() {
//        return action_name;
//    }
//
//    public Action_Type getAction_type() {
//        return action_type;
//    }
//
//  
//    
//    public Product getProduct() {
//        return product;
//    }
//
//    public Set<Command> getListCommand() {
//        return listCommand;
//    }
//
//    public void setAction_id(int action_id) {
//        this.action_id = action_id;
//    }
//
//    public void setAction_name(String action_name) {
//        this.action_name = action_name;
//    }
//
//    public void setAction_type(Action_Type action_type) {
//        this.action_type = action_type;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
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
//    @Override
//    public int hashCode() {
//        int hash = 5;
//        hash = 73 * hash + this.action_id;
//        hash = 73 * hash + Objects.hashCode(this.action_name);
//        hash = 73 * hash + Objects.hashCode(this.action_type);
//        hash = 73 * hash + Objects.hashCode(this.description);
//        hash = 73 * hash + Objects.hashCode(this.create_time);
//        hash = 73 * hash + Objects.hashCode(this.update_time);
//        hash = 73 * hash + Objects.hashCode(this.product);
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
//        final Action other = (Action) obj;
//        if (this.action_id != other.action_id) {
//            return false;
//        }
//        if (!Objects.equals(this.action_name, other.action_name)) {
//            return false;
//        }
//        if (!Objects.equals(this.description, other.description)) {
//            return false;
//        }
//        if (this.action_type != other.action_type) {
//            return false;
//        }
//        if (!Objects.equals(this.create_time, other.create_time)) {
//            return false;
//        }
//        if (!Objects.equals(this.update_time, other.update_time)) {
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
//        return "Action{" + "action_id=" + action_id + ", action_name=" + action_name + ", action_type=" + action_type + ", description=" + description + ", create_time=" + create_time + ", update_time=" + update_time + ", product=" + product + '}';
//    }
//    
//    
//    
//
//   
//}
