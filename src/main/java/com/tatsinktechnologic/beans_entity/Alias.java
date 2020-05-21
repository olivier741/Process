/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.beans_entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 *
 * @author olivier.tatsinkou
 */
@Entity
@Table(name = "alias", uniqueConstraints = {@UniqueConstraint(columnNames = {"msisdn", "alias", "service_id"})})
public class Alias implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int alias_id;

    private String msisdn;
    private String alias;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id", nullable = true)
    private Service service;
    
    @Column(nullable = true)
    @CreationTimestamp
    private Timestamp create_time;
    
    @Column(nullable = true)
    @UpdateTimestamp
    private Timestamp update_time;

    public int getAlias_id() {
        return alias_id;
    }

    public void setAlias_id(int alias_id) {
        this.alias_id = alias_id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.alias_id;
        hash = 37 * hash + Objects.hashCode(this.msisdn);
        hash = 37 * hash + Objects.hashCode(this.alias);
        hash = 37 * hash + Objects.hashCode(this.create_time);
        hash = 37 * hash + Objects.hashCode(this.update_time);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Alias other = (Alias) obj;
        if (this.alias_id != other.alias_id) {
            return false;
        }
        if (!Objects.equals(this.msisdn, other.msisdn)) {
            return false;
        }
        if (!Objects.equals(this.alias, other.alias)) {
            return false;
        }
        if (!Objects.equals(this.create_time, other.create_time)) {
            return false;
        }
        if (!Objects.equals(this.update_time, other.update_time)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Alias{" + "alias_id=" + alias_id + ", msisdn=" + msisdn + ", alias=" + alias + ", create_time=" + create_time + ", update_time=" + update_time + '}';
    }
    
    
}
