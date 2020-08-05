/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.process.restfull;

import com.tatsinktech.process.model.register.Register;
import com.tatsinktech.process.model.repository.RegisterRepository;
import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author olivier.tatsinkou
 */
@RestController
@RequestMapping("/process")
public class RegisterAPI {

    private static Logger logger = LoggerFactory.getLogger(RegisterAPI.class);

    @Autowired
    private RegisterRepository registerRepo;

    @GetMapping("/viewRegisterInfo")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> viewRegisterInfo(@RequestBody String resquest, HttpServletRequest request) {

        String responseAsString = null;
        try {
            JSONObject receivedJsonObject = new JSONObject(resquest);

            logger.info(" -- request recieve  -- " + receivedJsonObject.toString());

            String msisdn = receivedJsonObject.getString("msisdn");
            String service_name = receivedJsonObject.getString("serviceName");

            Register reg = registerRepo.findRegisterByMsisdnAndserviceName(msisdn, service_name);

            JSONObject jsonObject = new JSONObject();
            if (reg != null) {
                int status = reg.getStatus();

                jsonObject.put("transaction_id", reg.getTransactionId());
                jsonObject.put("msisdn", reg.getMsisdn());

                if (status == 1) {
                    jsonObject.put("state", "ACTIVE");
                }

                if (status == 0 || status == -1) {
                    jsonObject.put("state", "CANCEL");
                }

                if (status == 2) {
                    jsonObject.put("state", "PENDING");
                }

                if (status == -2) {
                    jsonObject.put("state", "EXPIRE");
                }

            } else {
                jsonObject.put("transaction_id", "");
                jsonObject.put("msisdn", reg.getMsisdn());
                jsonObject.put("state", "NO_REGISTER");
            }
            responseAsString = jsonObject.toString();

            logger.info(" -- VIEW REGISTER INFO -- " + responseAsString);
        } catch (JSONException e) {
            logger.error(e.getMessage());
        }

        return new ResponseEntity<String>(responseAsString, HttpStatus.OK);
    }
}
