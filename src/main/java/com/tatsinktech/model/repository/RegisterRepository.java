/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.model.repository;

import com.tatsinktech.model.register.Register;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author olivier
 */
@Repository
public interface RegisterRepository extends JpaRepository<Register, Long> {

    @Query("SELECT reg FROM Register reg WHERE reg.status = 1 AND reg.msisdn=: msisdn")
    List<Register> findAllActiveRegisterByMsisdn(String msisdn);
}
