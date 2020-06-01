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
public class Command_Request {
    private int command_id;
    private String command_code;
    private String param_pattern_separate;
    private String channel;

    public int getCommand_id() {
        return command_id;
    }

    public void setCommand_id(int command_id) {
        this.command_id = command_id;
    }

    public String getCommand_code() {
        return command_code;
    }

    public void setCommand_code(String command_code) {
        this.command_code = command_code;
    }

    public String getParam_pattern_separate() {
        return param_pattern_separate;
    }

    public void setParam_pattern_separate(String param_pattern_separate) {
        this.param_pattern_separate = param_pattern_separate;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + this.command_id;
        hash = 31 * hash + (this.command_code != null ? this.command_code.hashCode() : 0);
        hash = 31 * hash + (this.param_pattern_separate != null ? this.param_pattern_separate.hashCode() : 0);
        hash = 31 * hash + (this.channel != null ? this.channel.hashCode() : 0);
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
        final Command_Request other = (Command_Request) obj;
        if (this.command_id != other.command_id) {
            return false;
        }
        if ((this.command_code == null) ? (other.command_code != null) : !this.command_code.equals(other.command_code)) {
            return false;
        }
        if ((this.param_pattern_separate == null) ? (other.param_pattern_separate != null) : !this.param_pattern_separate.equals(other.param_pattern_separate)) {
            return false;
        }
        if ((this.channel == null) ? (other.channel != null) : !this.channel.equals(other.channel)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Command_Request{" + "command_id=" + command_id + ", command_code=" + command_code + ", param_pattern_separate=" + param_pattern_separate + ", channel=" + channel + '}';
    }
    
    
}
