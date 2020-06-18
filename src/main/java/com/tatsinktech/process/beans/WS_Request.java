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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
@XmlRootElement(name = "Request")
public class WS_Request  implements Serializable{
   
    private String msisdn;
    private String transactionId;
    private long amount;
    private String productCode;
    private String charge_reason;
    private String wsClientlogin;
    private String wsClientpassword;
    private Date request_time;
    private String ws_AccessMgntName;
    private String processUnit;
    private List<Param> WSparam = new ArrayList<Param>();
}
