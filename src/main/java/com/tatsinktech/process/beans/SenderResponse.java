/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.process.beans;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author olivier.tatsinkou
 */
@XmlRootElement(name = "MyResponse") 
public class SenderResponse  implements Serializable {
    
    
    private String response;
    private String message_id;
    private int value;
    private String description;

    public SenderResponse() {
      this.message_id="null";
      this.response = "null";
      this.value = -1;
      this.description = "null";   
    }

    public SenderResponse( String response, String message_id,int value, String description) {
        this.message_id = message_id;
        this.response = response;
        this.value = value;
        this.description = description;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Notification_Msg{" + "message_id=" + message_id + ", response=" + response + ", value=" + value + ", description=" + description + '}';
    }

}
