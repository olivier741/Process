/*
 * Copyright 2019 olivier.tatsinkou.
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

import java.util.Objects;

/**
 *
 * @author olivier.tatsinkou
 */
public class Message_Exchg {
    
    private String transaction_id;
    private String service_id;
    private String message_id;
    private String sender;
    private String receiver;
    private String content;
    private String exchange_mode;
    

    public Message_Exchg() {
    }

    public Message_Exchg(String transaction_id, String service_id, String message_id, String sender, String receiver, String content,String exchange_mode) {
        this.transaction_id = transaction_id;
        this.service_id = service_id;
        this.message_id = message_id;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.exchange_mode=exchange_mode;
    }

    

    public String getTransaction_id() {
        return transaction_id;
    }

    public String getService_id() {
        return service_id;
    }

    public String getMessage_id() {
        return message_id;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }
    
    

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExchange_mode() {
        return exchange_mode;
    }

    public void setExchange_mode(String exchange_mode) {
        this.exchange_mode = exchange_mode;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.transaction_id);
        hash = 29 * hash + Objects.hashCode(this.service_id);
        hash = 29 * hash + Objects.hashCode(this.message_id);
        hash = 29 * hash + Objects.hashCode(this.sender);
        hash = 29 * hash + Objects.hashCode(this.receiver);
        hash = 29 * hash + Objects.hashCode(this.content);
        hash = 29 * hash + Objects.hashCode(this.exchange_mode);
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
        final Message_Exchg other = (Message_Exchg) obj;
        if (!Objects.equals(this.transaction_id, other.transaction_id)) {
            return false;
        }
        if (!Objects.equals(this.service_id, other.service_id)) {
            return false;
        }
        if (!Objects.equals(this.message_id, other.message_id)) {
            return false;
        }
        if (!Objects.equals(this.sender, other.sender)) {
            return false;
        }
        if (!Objects.equals(this.receiver, other.receiver)) {
            return false;
        }
        if (!Objects.equals(this.content, other.content)) {
            return false;
        }
        if (!Objects.equals(this.exchange_mode, other.exchange_mode)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Message_Exchg{" + "transaction_id=" + transaction_id + ", service_id=" + service_id + ", message_id=" + message_id + ", sender=" + sender + ", receiver=" + receiver + ", content=" + content + ", exchange_mode=" + exchange_mode + '}';
    }


}
