/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.process.services;

import com.tatsinktech.process.model.register.Promo_Filter;
import com.tatsinktech.process.model.register.Promotion;
import com.tatsinktech.process.model.register.PromotionTable;
import com.tatsinktech.process.model.repository.PromotionTableRepository;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author olivier
 */
@Service
public class CommunService {

    @Autowired
    private PromotionTableRepository promotabRepo;

    public boolean authorizationPromo(String msisdn, Promotion promo) {
        boolean result = false;
        if (promo != null) {
            Promo_Filter promoFilter = promo.getPromotionFilter();

            switch (promoFilter) {
                case REGEX:
                    Pattern pattern_promo = Pattern.compile(promo.getMsisdnRegex());
                    result = pattern_promo.matcher(msisdn).find();
                    break;
                case TABLE:
                    PromotionTable promotab = promotabRepo.findAllActiveRegisterByMsisdn(msisdn, promo.getPromotionName());
                    if (promotab != null) {
                        result = true;
                    } else {
                        result = false;
                    }
                    break;
                case NONE:
                    result = true;
                    break;
                default:
                    result = true;
                    break;
            }
        }

        return result;
    }

}
