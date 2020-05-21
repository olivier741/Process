/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.model.repository;

import com.tatsinktech.model.register.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author olivier
 */
@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long>{
    
}
