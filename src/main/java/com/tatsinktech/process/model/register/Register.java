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
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
@Table(name = "register" ,
        indexes = {
                @Index(columnList = "msisdn", name = "msisdn_register_idx"),
                @Index(columnList = "transaction_id", name = "transaction_register_idx")
        },
        uniqueConstraints = {
    @UniqueConstraint(columnNames = {"transaction_id", "msisdn", "product_id"})})
public class Register extends AbstractModel<Long> {

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "msisdn")
    private String msisdn;

    /**
     * 1 = active|active, 
     * 0 = active|cancel, 
     * 2 = active|pending, 
     * -1= cancel|cancel (state in network|state in service)
     */
    @Column(name = "status")
    private int status;

    @Column(name = "autoextend")
    private boolean autoextend;

    @Column(name = "reg_time")
    private Date regTime;

    @UpdateTimestamp
    @Column(name = "renew_time")
    private Date renewTime;

    @Column(name = "expire_time")
    private Date expireTime;

    @Column(name = "unreg_time")
    private Date unregTime;

    @Column(name = "cancel_time")
    private Date cancelTime;

    @Column(name = "number_reg")
    private long numberReg;
    
    @Column(name = "product_code")
    private String productCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

}
