/*
 * Copyright 2018 olivier.tatsinkou.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tatsinktech.process.beans;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author olivier.tatsinkou
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DeliveryMessage", namespace="http://com.tatsinktechnologic.Receiver_SMS")
public class DeliveryMessage implements Serializable{
    
    @XmlElement(name = "message_id", required = true)
    private String message_id;
    
    @XmlElement(name = "sender", required = true)
    private String sender;
    
    @XmlElement(name = "receiver", required = true)
    private String receiver;
    
    @XmlElement(name = "delivery_id", required = true)
    private String delivery_id;
    
    @XmlElement(name = "delivery_sub", required = true)
    private String delivery_sub;
    
    @XmlElement(name = "delivery_dlvrd", required = true)
    private String delivery_dlvrd;
    
    @XmlElement(name = "delivery_submitDate", required = true)
    private String delivery_submitDate;
    
    @XmlElement(name = "delivery_doneDate", required = true)
    private String delivery_doneDate;
    
    @XmlElement(name = "delivery_status", required = true)
    private String delivery_status;
    
    @XmlElement(name = "delivery_err", required = true)
    private String delivery_err;
    
    @XmlElement(name = "delivery_text", required = true)
    private String delivery_text;

    public DeliveryMessage() {
        this.message_id = null;
        this.sender = null;
        this.receiver = null;
        this.delivery_id = null;
        this.delivery_sub = null;
        this.delivery_dlvrd = null;
        this.delivery_submitDate = null;
        this.delivery_doneDate = null;
        this.delivery_status = null;
        this.delivery_err = null;
        this.delivery_text = null;
    }
    
    

    public DeliveryMessage(String message_id, String sender, String receiver, String delivery_id, String delivery_sub, String delivery_dlvrd, String delivery_submitDate, String delivery_doneDate, String delivery_status, String delivery_err, String delivery_text) {
        this.message_id = message_id;
        this.sender = sender;
        this.receiver = receiver;
        this.delivery_id = delivery_id;
        this.delivery_sub = delivery_sub;
        this.delivery_dlvrd = delivery_dlvrd;
        this.delivery_submitDate = delivery_submitDate;
        this.delivery_doneDate = delivery_doneDate;
        this.delivery_status = delivery_status;
        this.delivery_err = delivery_err;
        this.delivery_text = delivery_text;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(String delivery_id) {
        this.delivery_id = delivery_id;
    }

    public String getDelivery_sub() {
        return delivery_sub;
    }

    public void setDelivery_sub(String delivery_sub) {
        this.delivery_sub = delivery_sub;
    }

    public String getDelivery_dlvrd() {
        return delivery_dlvrd;
    }

    public void setDelivery_dlvrd(String delivery_dlvrd) {
        this.delivery_dlvrd = delivery_dlvrd;
    }

    public String getDelivery_submitDate() {
        return delivery_submitDate;
    }

    public void setDelivery_submitDate(String delivery_submitDate) {
        this.delivery_submitDate = delivery_submitDate;
    }

    public String getDelivery_doneDate() {
        return delivery_doneDate;
    }

    public void setDelivery_doneDate(String delivery_doneDate) {
        this.delivery_doneDate = delivery_doneDate;
    }

    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    public String getDelivery_err() {
        return delivery_err;
    }

    public void setDelivery_err(String delivery_err) {
        this.delivery_err = delivery_err;
    }

    public String getDelivery_text() {
        return delivery_text;
    }

    public void setDelivery_text(String delivery_text) {
        this.delivery_text = delivery_text;
    }

    @Override
    public String toString() {
        return "DeliveryMessage{" + "message_id=" + message_id + ", sender=" + sender + ", receiver=" + receiver + ", delivery_id=" + delivery_id + ", delivery_sub=" + delivery_sub + ", delivery_dlvrd=" + delivery_dlvrd + ", delivery_submitDate=" + delivery_submitDate + ", delivery_doneDate=" + delivery_doneDate + ", delivery_status=" + delivery_status + ", delivery_err=" + delivery_err + ", delivery_text=" + delivery_text + '}';
    }
}
