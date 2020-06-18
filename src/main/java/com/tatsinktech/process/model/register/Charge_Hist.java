/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.process.model.register;

import com.tatsinktech.process.model.AbstractModel;
import javax.persistence.Entity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Check;
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
@Check(constraints = "charge_fee >= 0")
@Table(name = "charge_hist",
        indexes = {
                @Index(columnList = "msisdn", name = "msisdn_chargehist_idx"),
                @Index(columnList = "transaction_id", name = "transaction_chargehist_idx")
        })
public class Charge_Hist extends AbstractModel<Long> {

    @Column(name = "transaction_id")
    private String transactionId;
    
    @Column(name = "msisdn")
    private String msisdn;

    @Enumerated(EnumType.STRING)
    @Column(name = "charge_mode")
    private Charge_Event chargeMode;

    @Column(name = "charge_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date chargeTime;
    
    @Column(name = "charge_fee")
    private long chargeFee;

    @Lob
    @Column(name = "charge_request")
    private String chargeRequest;

    @Lob
    @Column(name = "charge_response")
    private String chargeResponse;

    @Column(name = "duration")
    private long duration;

    @Column(name = "charge_error")
    private int chargeError;
    
    @Column(name = "productCode")
    private String productCode;

    @Column(name = "process_unit")
    private String processUnit;
    
    @Column(name = "Ip_address")
    private String IpAddress;

}
