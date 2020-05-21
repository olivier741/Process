///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tatsinktechnologic.beans_entity;
//
//
//import java.io.Serializable;
//import java.sql.Timestamp;
//import java.util.Objects;
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
//import javax.persistence.Table;
//import javax.persistence.UniqueConstraint;
//import org.hibernate.annotations.CreationTimestamp;
//
///**
// *
// * @author olivier.tatsinkou
// */
//@Entity
//@Table(name = "chatgroup_register",uniqueConstraints = {@UniqueConstraint(columnNames = {"chatgroup_id", "register_id"})})
//public class ChatGroup_Register implements Serializable{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(unique = true, nullable = false)
//    private int chatgroup_reg_id;
//    
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "chatgroup_id", nullable = false)
//    private ChatGroup chatgroup;
//    
//    @Enumerated(EnumType.STRING)
//    private Chat_Level chat_level;
//    
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "register_id", nullable = false)
//    private Register register;
//    
//    @Column(nullable = true)
//    @CreationTimestamp
//    private Timestamp create_time;
//
//    public int getChatgroup_reg_id() {
//        return chatgroup_reg_id;
//    }
//
//    public void setChatgroup_reg_id(int chatgroup_reg_id) {
//        this.chatgroup_reg_id = chatgroup_reg_id;
//    }
//
//    public ChatGroup getChatgroup() {
//        return chatgroup;
//    }
//
//    public void setChatgroup(ChatGroup chatgroup) {
//        this.chatgroup = chatgroup;
//    }
//
//    public Chat_Level getChat_level() {
//        return chat_level;
//    }
//
//    public void setChat_level(Chat_Level chat_level) {
//        this.chat_level = chat_level;
//    }
//    
//    
//
//    public Register getRegister() {
//        return register;
//    }
//
//    public void setRegister(Register register) {
//        this.register = register;
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
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 59 * hash + this.chatgroup_reg_id;
//        hash = 59 * hash + Objects.hashCode(this.chatgroup);
//        hash = 59 * hash + Objects.hashCode(this.register);
//        hash = 59 * hash + Objects.hashCode(this.create_time);
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
//        final ChatGroup_Register other = (ChatGroup_Register) obj;
//        if (this.chatgroup_reg_id != other.chatgroup_reg_id) {
//            return false;
//        }
//        if (!Objects.equals(this.chatgroup, other.chatgroup)) {
//            return false;
//        }
//        if (!Objects.equals(this.register, other.register)) {
//            return false;
//        }
//        if (!Objects.equals(this.create_time, other.create_time)) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "ChatGroup_Register{" + "chatgroup_reg_id=" + chatgroup_reg_id + ", chatgroup=" + chatgroup + ", register=" + register + ", create_time=" + create_time + '}';
//    }
//    
//    
//}
