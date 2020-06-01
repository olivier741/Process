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
public class Command_Param {
    private int param_id;
    private String param_name;
    private int param_length;
    private String param_pattern;

    public int getParam_id() {
        return param_id;
    }

    public void setParam_id(int param_id) {
        this.param_id = param_id;
    }

    public String getParam_name() {
        return param_name;
    }

    public void setParam_name(String param_name) {
        this.param_name = param_name;
    }

    public int getParam_length() {
        return param_length;
    }

    public void setParam_length(int param_length) {
        this.param_length = param_length;
    }

    public String getParam_pattern() {
        return param_pattern;
    }

    public void setParam_pattern(String param_pattern) {
        this.param_pattern = param_pattern;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + this.param_id;
        hash = 61 * hash + (this.param_name != null ? this.param_name.hashCode() : 0);
        hash = 61 * hash + this.param_length;
        hash = 61 * hash + (this.param_pattern != null ? this.param_pattern.hashCode() : 0);
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
        final Command_Param other = (Command_Param) obj;
        if (this.param_id != other.param_id) {
            return false;
        }
        if (this.param_length != other.param_length) {
            return false;
        }
        if ((this.param_name == null) ? (other.param_name != null) : !this.param_name.equals(other.param_name)) {
            return false;
        }
        if ((this.param_pattern == null) ? (other.param_pattern != null) : !this.param_pattern.equals(other.param_pattern)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Command_Param{" + "param_id=" + param_id + ", param_name=" + param_name + ", param_length=" + param_length + ", param_pattern=" + param_pattern + '}';
    }
    

    
}
