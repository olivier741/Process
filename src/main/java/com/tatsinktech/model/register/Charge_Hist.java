/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.model.register;

import com.tatsinktech.model.AbstractModel;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
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

    @UpdateTimestamp
     @Column(name = "charge_time")
    private Timestamp chargeTime;
    
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
    private String chargeError;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    @Column(name = "process_unit")
    private String processUnit;
    
    @Column(name = "Ip_address")
    private String IpAddress;

}
