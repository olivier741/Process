/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.process.model.repository;

import com.tatsinktech.process.model.register.PromotionTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author olivier.tatsinkou
 */
@Repository
public interface PromotionTableRepository extends JpaRepository<PromotionTable, Long>{
    
    @Query("SELECT proTab FROM PromotionTable proTab WHERE proTab.msisdn= :msisdn AND proTab.promotion.promotionName =:promoName")
    PromotionTable findAllActiveRegisterByMsisdn(String msisdn,String promoName);

}
