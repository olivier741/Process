/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.process.beans;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
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
@XmlRootElement(name = "Response")
public class WS_Response implements Serializable{
    
    private String msisdn;
    private String transactionId;
    private String ws_AccessMgntName;
    private String API_GW_Error;
    private String API_GW_Description;
    private String WS_ERROR;
    private String WS_Description;
    private String WS_ResponseContent;
    private String WS_request_time;

}