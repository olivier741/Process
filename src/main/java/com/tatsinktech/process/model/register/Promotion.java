/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.process.model.register;

import com.tatsinktech.process.model.AbstractModel;
import javax.persistence.Entity;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "promotion")
@Check(constraints = "promotion_reg_fee >= 0 and promotion_ext_fee >=0")
public class Promotion extends AbstractModel<Long> {
    
    @Column(name ="promotion_name",nullable = false, unique = true)
    private String promotionName;
    
    @Enumerated(EnumType.STRING)
    @Column(name ="promotion_filter")
    private Promo_Filter promotionFilter;
        
    @Column(name ="istableselected",nullable = true)
    private Boolean isTableSelected;
    
    @Column(name ="msisdn_regex",nullable = true)
    private String msisdnRegex;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name ="start_time",nullable = true)
    public Date startTime;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name ="end_time",nullable = true)
    public Date endTime;
    
    @Enumerated(EnumType.STRING)
    @Column(name ="reduction_mode",nullable = true)
    private Reduction_Type reductionMode;

    @Column(name ="promotion_reg_fee",nullable = true)
    private Long promotionRegFee;
    
    @Column(name ="percentage_reg",nullable = true)
    private Long percentageReg;
    
    @Column(name ="isextend",nullable = true)
    private Boolean isExtend;
    
    @Column(name ="promotion_ext_fee",nullable = true)
    private Long promotionExtFee;
    
    @Column(name ="percentage_ext",nullable = true)
    private Long percentageExt;

    @OneToMany(mappedBy = "promotion")
    protected Set<Product> listProduct = new HashSet<>();
    
    @OneToMany(mappedBy = "promotion")
    protected Set<PromotionTable> listPromTable = new HashSet<>();


   
}
