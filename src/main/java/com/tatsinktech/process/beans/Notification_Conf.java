/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.process.beans;

/**
 *
 * @author olivier.tatsinkou
 */
public class Notification_Conf {
    private int notification_id;
    private String language;
    private String param_name;
    private String param_value;

    public int getNotification_id() {
        return notification_id;
    }

    public String getLanguage() {
        return language;
    }

    public String getParam_name() {
        return param_name;
    }

    public String getParam_value() {
        return param_value;
    }

    public void setNotification_id(int notification_id) {
        this.notification_id = notification_id;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setParam_name(String param_name) {
        this.param_name = param_name;
    }

    public void setParam_value(String param_value) {
        this.param_value = param_value;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.notification_id;
        hash = 67 * hash + (this.language != null ? this.language.hashCode() : 0);
        hash = 67 * hash + (this.param_name != null ? this.param_name.hashCode() : 0);
        hash = 67 * hash + (this.param_value != null ? this.param_value.hashCode() : 0);
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
        final Notification_Conf other = (Notification_Conf) obj;
        if (this.notification_id != other.notification_id) {
            return false;
        }
        if ((this.language == null) ? (other.language != null) : !this.language.equals(other.language)) {
            return false;
        }
        if ((this.param_name == null) ? (other.param_name != null) : !this.param_name.equals(other.param_name)) {
            return false;
        }
        if ((this.param_value == null) ? (other.param_value != null) : !this.param_value.equals(other.param_value)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Notification_Conf{" + "notification_id=" + notification_id + ", language=" + language + ", param_name=" + param_name + ", param_value=" + param_value + '}';
    }


    
}
