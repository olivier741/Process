/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.process.model.register;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

/**
 *
 * @author olivier.tatsinkou
 */
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Subselect(
        "   SELECT cmd.id,  \n"
        + "        cmd.command_name,  \n"
        + "        cmd.command_code,  \n"
        + "	   cmd.split_separator, \n"
        + "	   ac.action_name,  \n"
        + "	   ac.action_type,  \n"
        + "	   prod.product_code, \n"
        + "	   prod.reg_fee,  \n"
        + "	   prod.restrict_product, \n"
        + "	   prod.start_time, \n"
        + "	   prod.end_time,   \n"
        + "	   prod.restrict_constant_validity,   \n"
        + "	   prod.isframe_validity, \n"
        + "	   prod.frame_time_validity, \n"
        + "	   prod.validity, \n"
        + "	   prod.pending_duration, \n"
        + "	   prod.isextend, \n"
        + "	   prod.isoveride_reg,   \n"
        + "	   prod.isnotify_extend, \n"
        + "	   prod.extend_fee, \n"
        + "        promo.promotion_name, \n"
        + "        promo.promotion_filter, \n"
        + "        promo.istableselected, \n"
        + "        promo.msisdn_regex, \n"
        + "        promo.start_time promo_start_time, \n"
        + "        promo.end_time promo_end_time, \n"
        + "        promo.reduction_mode, \n"
        + "        promo.promotion_reg_fee, \n"
        + "        promo.percentage_reg, \n"
        + "        promo.isextend promo_isextend, \n"
        + "        promo.promotion_ext_fee, \n"
        + "        promo.percentage_ext, \n"
        + "	   ser.service_name, \n"
        + "        ser.receive_channel, \n"
        + "        ser.send_channel, \n"
        + "        ser.service_provider, \n"
        + "	   pr.param_name,   \n"
        + "	   pr.param_length,    \n"
        + "	   pr.param_pattern   \n"
        + "   FROM command cmd   \n"
        + "   LEFT JOIN parameter pr ON cmd.id = pr.id \n"
        + "   LEFT JOIN action ac ON cmd.action_id = ac.id \n"
        + "   LEFT JOIN product prod ON ac.product_id = prod.id \n"
        + "   LEFT JOIN promotion promo ON prod.promotion_id = promo.id \n"
        + "   LEFT JOIN service ser ON prod.service_id = ser.id ")
@Synchronize({"Command", "Parameter", "Action", "Product", "Promotion", "ServiceProvider"})
@Immutable
public class Request_Conf implements Serializable {

    @Id
    private long id;

    // -------------- Command ---------------
    @Column(name = "command_name")
    private String commandName;

    @Column(name = "command_code")
    private String commandCode;

    @Column(name = "split_separator")
    private String splitSeparator;

    //------------ Action ---------------------
    @Column(name = "action_name")
    private String actionName;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type")
    private Action_Type actionType;

    // ------------------- Product ---------------
    @Column(name = "product_code")
    private String productCode;

    @Column(name = "reg_fee")
    private Long regFee;

    // list of restric product separate by | (e.g : CAN1|CAN2)
    @Column(name = "restrict_product")
    private String restrictProduct;

    //  2019-04-16 23:00:01-07:00:00  this offer will launch  the 2019-04-16 at 11PM 
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time")
    public Date startTime;

    //  2050-04-16 23:00:01-07:00:00  this offer will end  the 2050-04-16 at 11PM 
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    public Date endTime;

    /*  the Day or hour where customer is not allow to get the service.
        following the type of constant validity (D or H). we must set information as following : 
        - (1|2|3) 0-Sunday, 1-Monday, 2-Tuesday, 3-Wednesday, ... not information mean registration every day'
        - (1|2) 00:00 to 00:59 , 01:00 to 01:59,  ... not information mean registration every time'
     */
    @Column(nullable = true)
    private String restrictConstantValidity;

    /* This allow to select the thype of validity : Frame or Constant. Frame validity is the validity which go
       from start_time to end_time and only allow in the frame time by day 
     */
    @Column(name = "isframe_validity")
    private Boolean isFrameValidity;

    //  07:00:00-13:00:00  this validy will allow service from 07AM to 01PM
    @Column(name = "frame_time_validity")
    private String frameTimeValidity;

// D1 mean customer must have this offer for one Day, H5 mean customer must have this offer for 5 hours
    @Column(name = "validity")
    private String validity;

    // D30 mean customer pending 30 Day on this offert, he is cancel (system will not try to extend) 
    @Column(name = "pending_duration")
    private String pendingDuration;

    @Column(name = "isextend")
    private Boolean isExtend;

    @Column(name = "isoveride_reg")
    private Boolean isOverideReg;

    @Column(name = "isnotify_extend")
    private Boolean isNotifyExtend;

    @Column(name = "extend_fee")
    private Long extendFee;

    // ----------------- Promotion -----------------
    @Column(name = "promotion_name")
    private String promotionName;

    @Enumerated(EnumType.STRING)
    @Column(name = "promotion_filter")
    private Promo_Filter promotionFilter;

    @Column(name ="istableselected",nullable = true)
    private Boolean isTableSelected;

    @Column(name = "msisdn_regex")
    private String msisdnRegex;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "promo_start_time")
    public Date promo_startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "promo_end_time")
    public Date promo_endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "reduction_mode")
    private Reduction_Type reductionMode;

    @Column(name = "promotion_reg_fee")
    private Long promotionRegFee;

    @Column(name = "percentage_reg")
    private Long percentageReg;

    @Column(name = "promo_isextend")
    private Boolean promo_isExtend;

    @Column(name = "promotion_ext_fee")
    private Long promotionExtFee;

    @Column(name = "percentage_ext")
    private Long percentageExt;

    //-------------------- ServiceProvider ------------------
    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "receive_channel")
    private String receiveChannel;

    @Column(name = "send_channel")
    private String sendChannel;

    @Column(name = "service_provider")
    private String serviceProvider;

    //-------------------- Parameter --------------------------
    @Column(name = "param_name")
    private String paramName;

    @Column(name = "param_length")
    private Integer paramLength;

    @Column(name = "param_pattern")
    private String paramPattern;
    
    

}
