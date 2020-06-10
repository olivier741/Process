/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.process.model.register;


import com.tatsinktech.process.model.AbstractModel;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "product")
@Check(constraints = "reg_fee >= 0 AND extend_fee >= 0")
public class Product extends AbstractModel<Long> {

    @Column(name="product_code",nullable = false, unique = true)
    private String productCode;

    @Column(name="reg_fee",nullable = true)
    private Long regFee;

    // list of restric product separate by | (e.g : CAN1|CAN2)
    @Column(name="restrict_product",nullable = true)
    private String restrictProduct;

    //  2019-04-16 23:00:01-07:00:00  this offer will launch  the 2019-04-16 at 11PM 
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="start_time",nullable = true)
    public Date startTime;

    //  2050-04-16 23:00:01-07:00:00  this offer will end  the 2050-04-16 at 11PM 
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="end_time",nullable = true)
    public Date endTime;

    /* This allow to select the thype of validity : Frame or Constant. Frame validity is the validity which go
       from start_time to end_time and only allow in the frame time by day 
     */
    @Column(name="isframe_validity",nullable = true)
    private Boolean isFrameValidity = false;

    /*  the Day or hour where customer is not allow to get the service.
        following the type of constant validity (D or H). we must set information as following : 
        - (1|2|3) 0-Sunday, 1-Monday, 2-Tuesday, 3-Wednesday, ... not information mean registration every day'
        - (1|2) 00:00 to 00:59 , 01:00 to 01:59,  ... not information mean registration every time'
     */
    @Column(name="restrict_constant_validity",nullable = true)
    private String restrictConstantValidity;

    //  07:00:00-13:00:00  this validy will allow service from 07AM to 01PM
    @Column(name="frame_time_validity",nullable = true)
    private String frameTimeValidity;

    // D1 mean customer must have this offer for one Day, H5 mean customer must have this offer for 5 hours
    @Column(name="validity",nullable = true)
    private String validity;

    // D30 mean customer pending 30 Day on this offert, he is cancel (system will not try to extend) 
    @Column(name="pending_duration",nullable = true)
    private String pendingDuration;

    @Column(name="isextend",nullable = true)
    private Boolean isExtend = true;

    @Column(name="isoveride_reg",nullable = true)
    private Boolean isOverideReg = true;

     @Column(name="isnotify_extend",nullable = true)
    private Boolean isNotifyExtend = true;

    @Column(name="extend_fee",nullable = true)
    private Long extendFee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promotion_id", nullable = true)
    private Promotion promotion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id", nullable = true)
    private ServiceProvider service;

    @OneToMany(mappedBy = "product")
    private Set<Action> listAction = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<Register> listRegister = new HashSet<>();

}
