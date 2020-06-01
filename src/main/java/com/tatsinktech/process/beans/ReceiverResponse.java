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
@XmlRootElement(name = "Response") 
public class ReceiverResponse  implements Serializable {
    
    
    private String response;
    private int value;
    private String description;

    public ReceiverResponse() {
      this.response = "null";
      this.value = -1;
      this.description = "null";   
    }

    public ReceiverResponse( String response,int value, String description) {
        this.response = response;
        this.value = value;
        this.description = description;
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
        return "Response_Msg{" + "response=" + response + ", value=" + value + ", description=" + description + '}';
    }

   
}
