/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.resfull.bean;

import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author olivier.tatsinkou
 */
@XmlRootElement(name = "account")
public class Credential implements Serializable {
    private String ws_client_name;
    private String ws_block_api_name;
    private String user_name;
    private String password;
    private String token;
    private String Roles;

    public String getWs_client_name() {
        return ws_client_name;
    }

    public void setWs_client_name(String ws_client_name) {
        this.ws_client_name = ws_client_name;
    }

    public String getWs_block_api_name() {
        return ws_block_api_name;
    }

    public void setWs_block_api_name(String ws_block_api_name) {
        this.ws_block_api_name = ws_block_api_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRoles() {
        return Roles;
    }

    public void setRoles(String Roles) {
        this.Roles = Roles;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.ws_client_name);
        hash = 67 * hash + Objects.hashCode(this.user_name);
        hash = 67 * hash + Objects.hashCode(this.password);
        hash = 67 * hash + Objects.hashCode(this.token);
        hash = 67 * hash + Objects.hashCode(this.Roles);
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
        final Credential other = (Credential) obj;
        if (!Objects.equals(this.ws_client_name, other.ws_client_name)) {
            return false;
        }
       
        if (!Objects.equals(this.user_name, other.user_name)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.token, other.token)) {
            return false;
        }
        if (!Objects.equals(this.Roles, other.Roles)) {
            return false;
        }
        return true;
    }
    
    
    
}
