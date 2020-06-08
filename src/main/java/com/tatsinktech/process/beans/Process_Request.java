/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.process.beans;

import com.tatsinktech.process.model.register.Action_Type;
import java.sql.Timestamp;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author olivier.tatsinkou
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Process_Request {

    private String transaction_id;
    private String msisdn;
    private String receiver;
    private String content;
    private String commandName;
    private String commanCode;
    private String splitSeparate;

    private String paramName;
    private Integer paramLength;
    private String paramPattern;
    private String paramValue;
    private String sendChannel;
    private String rcvChannel;

    private String actionName;
    private Action_Type actionType;
    private Timestamp receiveTime;
    private String productCode;
    private String notificationCode;
    private String language;
    private String exchangeMode;
    private String serviceName;
    private HashMap<String, String> setvariable;

}
