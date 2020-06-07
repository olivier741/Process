/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.process.model.register;


import com.tatsinktech.process.model.AbstractModel;
import javax.persistence.Entity;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;

/**
 *
 * @author olivier
 */
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "mt_hist",
        indexes = {
                @Index(columnList = "msisdn", name = "msisdn_mthist_idx"),
                @Index(columnList = "transaction_id", name = "transaction_mthist_idx")
        })
public class Mt_Hist extends AbstractModel<Long>{

   @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "msisdn")
    private String msisdn;
    
    @Lob 
    @Column(name = "message")
    private String message;
    
    @Column(name = "channel")
    private String channel;
    
    @Column(name = "receive_time")
    private Timestamp receiveTime;
    
    @UpdateTimestamp
    @Column(name = "send_time")
    private Timestamp sendTime;
    
     @Column(name = "productCode")
    private String productCode;
     
    @Column(name = "commad_name")
    private String commandName;
    
    @Column(name = "commad_code")
    private String commandCode;
    
    @Column(name = "param_name")
    private String paramName;
     
    @Column(name = "service_name")
    private String serviceName;
   
    @Column(name = "process_unit")
    private String processUnit;

    @Column(name = "Ip_address")
    private String IpAddress;

}
