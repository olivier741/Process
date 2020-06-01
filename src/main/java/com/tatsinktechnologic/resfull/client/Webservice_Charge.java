/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.resfull.client;


import com.tatsinktechnologic.beans_entity.Charge_Event;
import com.tatsinktechnologic.beans_entity.Charge_Hist_Faillure;
import com.tatsinktechnologic.beans_entity.Charge_Hist_Success;
import com.tatsinktechnologic.beans_entity.Product;
import com.tatsinktechnologic.beans_entity.Promotion;
import com.tatsinktechnologic.beans_entity.Reduction_Type;
import com.tatsinktechnologic.dao_repository.Charge_Hist_FaillureJpaController;
import com.tatsinktechnologic.dao_repository.Charge_Hist_SuccessJpaController;
import com.tatsinktechnologic.loadconfig.Load_Configuration;
import com.tatsinktechnologic.resfull.bean.Credential;
import com.tatsinktechnologic.resfull.bean.Param;
import com.tatsinktechnologic.resfull.bean.WS_Block_Response;
import com.tatsinktechnologic.resfull.bean.WS_Request;
import com.tatsinktechnologic.resfull.bean.WS_Response;
import com.tatsinktechnologic.resfull.bean.WS_Tag_Attr;
import com.tatsinktech.process.util.ConverterXML_JSON;
import com.tatsinktech.process.util.Utils;
import com.tatsinktechnologic.xml.Alias;
import com.tatsinktechnologic.xml.Tag_Attr;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.WebServiceException;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.log4j.Logger;


/**
 *
 * @author olivier.tatsinkou
 */
public class Webservice_Charge {
    
    private static String token= "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyYW5kb20iOiI4NDU2MjBiYy1hYWViLTRjMzItYjZmMy03ODcxMzM4YWUwZTUiLCJ1c2VyX25hbWUiOiJwbys0d1NHYmFuVVhkODRCaUJ2cWlaS0svYUFNVnNOaUpSM05ZOTE0VEhRPSIsImlzcyI6InBvKzR3U0diYW5VWGQ4NEJpQnZxaVpLSy9hQU1Wc05pSlIzTlk5MTRUSFE9IiwiZXhwIjoxNTY4NzEzNDkxfQ.dx1JgIGIwTwa6v9JSTxgA7qYfaOt2dAKk1xpi_bIDLY";

    private static Logger logger = Logger.getLogger(Webservice_Charge.class);
    private EntityManagerFactory emf;
    private Load_Configuration commonConfig = Load_Configuration.getConfigurationLoader();
    private String charge_url; 
    private String charge_uri_auth;    
    private String charge_uri_service;
    private String charge_application_type;
    private String charge_client_name;
    private String charge_user;
    private String charge_password;
    private String charge_webservice_name;
    private int charge_con_timeout;
    private int charge_req_timeout;
    private  Alias charge_aliase ;
    
   
    
    private InetAddress address;

    public Webservice_Charge() {
            emf = commonConfig.getEmf();
        
            // charging
            charge_client_name=commonConfig.getCharging_api().getClient_name();
            charge_aliase=commonConfig.getCharging_api().getAlias();
            charge_user=commonConfig.getCharging_api().getUser();
            charge_password=commonConfig.getCharging_api().getPassword();
            charge_uri_auth=commonConfig.getCharging_api().getUri_auth();
            charge_uri_service=commonConfig.getCharging_api().getUri_service();
            charge_url=commonConfig.getCharging_api().getUrl();
            charge_con_timeout=commonConfig.getCharging_api().getWebservice_con_timeout();
            charge_req_timeout = commonConfig.getCharging_api().getWebservice_req_timeout();
            charge_webservice_name= commonConfig.getCharging_api().getWebservice_name();

            address = Utils.gethostName();
    }

    
    
    
    public WS_Block_Response requestCharge_Product(String msisdn,String transaction_id,Product product){
        
        
        Credential account = new Credential();
        account.setWs_client_name(charge_client_name);
        account.setWs_block_api_name(charge_webservice_name);
        account.setUser_name(charge_user);
        account.setPassword(charge_password);


       
        WS_Request request = new WS_Request();
        request.setMsisdn(msisdn);
        request.setReg_transaction_id(transaction_id);
        request.setWs_client_name(account.getWs_client_name());
        request.setWs_block_api_name(account.getWs_block_api_name());


        List<Param> listparam = new ArrayList<Param>();
        Param param_msisdn = new Param();
        Param param_fee = new Param();
        Param param_description = new Param();
        
        Promotion promo = product.getPromotion();
        String fee_value ="0";
        if (promo!=null){
            long product_fee = product.getReg_fee();
            long promotion_percentage = promo.getPercentage_reg();
            long promotion_fee = promo.getPromotion_reg_fee();
            
            if (promo.getReduction_mode()==Reduction_Type.PERCENTAGE){
               fee_value= String.valueOf(product_fee -  (product_fee*promotion_percentage/100)); 
            }else{
                fee_value= String.valueOf(promotion_fee);
            }
                
            param_description.setAlias(charge_aliase.getDescription());
            param_description.setValue(promo.getDescription());
            listparam.add(param_description);

            param_msisdn.setAlias(charge_aliase.getMsisdn());
            param_msisdn.setValue(msisdn);
            listparam.add(param_msisdn);

            param_fee.setAlias(charge_aliase.getFee());
            param_fee.setValue(fee_value);
            listparam.add(param_fee);
        }else{
            fee_value = String.valueOf(product.getReg_fee());
            param_description.setAlias(charge_aliase.getDescription());
            param_description.setValue(product.getDescription());
            listparam.add(param_description);

            param_msisdn.setAlias(charge_aliase.getMsisdn());
            param_msisdn.setValue(msisdn);
            listparam.add(param_msisdn);

            param_fee.setAlias(charge_aliase.getFee());
            param_fee.setValue(fee_value);
            listparam.add(param_fee);
        }

        request.setWSparam(listparam);

        String stringReq = ConverterXML_JSON.convertWS_RequestToXML(request);

        logger.info("request : \n" + stringReq);


        Timestamp beforeRequest_time = new Timestamp(System.currentTimeMillis());

        Response response = sendAPI_Request(charge_url,
                                        charge_uri_service,
                                        account,
                                        token,
                                        charge_con_timeout,
                                        charge_req_timeout,
                                        request);

        int error_code = response.getStatus();
        
        if (error_code!=200){
                        token= getToken(charge_url,
                               charge_uri_auth,
                               account,
                               charge_con_timeout,
                               charge_req_timeout);

                        response = sendAPI_Request(charge_url,
                                            charge_uri_service,
                                            account,
                                            token,
                                            charge_con_timeout,
                                            charge_req_timeout,
                                            request);
                        error_code = response.getStatus();
            }

           Timestamp receive_time = new Timestamp(System.currentTimeMillis());
           long diffInMS = (receive_time.getTime() - beforeRequest_time.getTime());
           WS_Block_Response resp = null; 
           
           if (error_code==200){
                resp = response.readEntity(WS_Block_Response.class);
                if (resp!= null){
                    resp.setFee_charge(Long.parseLong(fee_value));
                    String stringResp = ConverterXML_JSON.convertWS_Block_ResponseToXML(resp);

                    logger.info(charge_client_name+" request "+charge_webservice_name+" during : "+diffInMS+" ms");
                    logger.info("response : \n"+stringResp);
                    
                    Charge_Hist_Success charge_success = new Charge_Hist_Success();
            
                    charge_success.setCharge_error(resp.getListws_response().get(0).getWS_ERROR()+"|"+resp.getListws_response().get(0).getWS_Description());
                    charge_success.setCharge_fee(product.getReg_fee());
                    charge_success.setCharge_mode(Charge_Event.REGISTRATION);
                    charge_success.setCharge_request(stringReq);
                    charge_success.setCharge_response(stringResp);
                    charge_success.setCharge_time(receive_time);
                    charge_success.setDuration(diffInMS);
                    charge_success.setIP_unit(address.getHostName()+"@"+address.getHostAddress());
                    charge_success.setMsisdn(msisdn);
                    charge_success.setProcess_unit("Process_Reg");
                    charge_success.setProduct(product);
                    charge_success.setTransaction_id(transaction_id);

                    Charge_Hist_SuccessJpaController chargeHistsuccessCont = new Charge_Hist_SuccessJpaController(emf);
                    chargeHistsuccessCont.create(charge_success);
                }else{
                    Charge_Hist_Success charge_success = new Charge_Hist_Success();
                    charge_success.setCharge_error("-66");
                    charge_success.setCharge_fee(product.getReg_fee());
                    charge_success.setCharge_mode(Charge_Event.REGISTRATION);
                    charge_success.setCharge_request(stringReq);
                    charge_success.setCharge_response("Null Response");
                    charge_success.setCharge_time(receive_time);
                    charge_success.setDuration(diffInMS);
                    charge_success.setIP_unit(address.getHostName()+"@"+address.getHostAddress());
                    charge_success.setMsisdn(msisdn);
                    charge_success.setProcess_unit("Process_Reg");
                    charge_success.setProduct(product);
                    charge_success.setTransaction_id(transaction_id);

                    Charge_Hist_SuccessJpaController chargeHistsuccessCont = new Charge_Hist_SuccessJpaController(emf);
                    chargeHistsuccessCont.create(charge_success);
                }
                
                
            }else{
                    String stringResp = response.readEntity(String.class);

                    Charge_Hist_Faillure charge_fail = new Charge_Hist_Faillure();

                    charge_fail.setCharge_error(String.valueOf(response.getStatus()));
                    charge_fail.setCharge_fee(product.getReg_fee());
                    charge_fail.setCharge_mode(Charge_Event.REGISTRATION);
                    charge_fail.setCharge_request(stringReq);
                    charge_fail.setCharge_response(stringResp);
                    charge_fail.setCharge_time(receive_time);
                    charge_fail.setDuration(diffInMS);
                    charge_fail.setIP_unit(address.getHostName()+"@"+address.getHostAddress());
                    charge_fail.setMsisdn(msisdn);
                    charge_fail.setProcess_unit("Process_Reg");
                    charge_fail.setProduct(product);
                    charge_fail.setTransaction_id(transaction_id);

                    Charge_Hist_FaillureJpaController chargeHistfailCont = new Charge_Hist_FaillureJpaController(emf);
                    chargeHistfailCont.create(charge_fail);

                
           }

        return resp;
    }
    
     
    private String getToken(String url, String path,Credential account,int con_timeout,int rcv_timeout){
        String result = null;
       
            WebClient client_auth = WebClient.create(url);
            ClientConfiguration config = WebClient.getConfig(client_auth);

            config.getOutInterceptors().add(new LoggingOutInterceptor());
            config.getInInterceptors().add(new LoggingInInterceptor());
            
            HTTPConduit http = (HTTPConduit) WebClient.getConfig(client_auth).getConduit();
            HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();


          try {
              
                httpClientPolicy.setConnectionTimeout(con_timeout);
                httpClientPolicy.setReceiveTimeout(rcv_timeout);

                http.setClient(httpClientPolicy);
              
                Response resp_token = client_auth.path(path)
                        .accept(MediaType.APPLICATION_XML)
                        .type(MediaType.APPLICATION_XML)
                        .post(account);

                result = resp_token.readEntity(String.class);

                logger.info("Auth request : \n" + ConverterXML_JSON.convertCredentialToXML(account));
                logger.info("Auth token : " + result);
            
        } catch (WebServiceException wse) {
            logger.error("Cannot get Token 1",wse);
        } catch (Exception ex) {
            logger.error("Cannot get Token 2",ex);
        }
        
        return result;
    }
    
     private Response sendAPI_Request(String url,String path,Credential account,String token,int con_timeout,int rcv_timeout,WS_Request request){
       
        WebClient client_request = WebClient.create(url);
        ClientConfiguration configBook = WebClient.getConfig(client_request);

        configBook.getOutInterceptors().add(new LoggingOutInterceptor());
        configBook.getInInterceptors().add(new LoggingInInterceptor());

        HTTPConduit http = (HTTPConduit) WebClient.getConfig(client_request).getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        // indefined
        
        Response response = null;
        
        try {

            httpClientPolicy.setConnectionTimeout(con_timeout);
            // indefined
            httpClientPolicy.setReceiveTimeout(rcv_timeout);

            http.setClient(httpClientPolicy);

            response = client_request.path(path).accept(MediaType.APPLICATION_XML)
                    .type(MediaType.APPLICATION_XML)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .header("Account", account.getWs_client_name() + "_" + account.getWs_block_api_name())
                    .post(request, Response.class);
            
        } catch (WebServiceException wse) {
           logger.error("Webservice Error 1",wse);
           response= Response.status(Response.Status.BAD_GATEWAY)
                        .header(HttpHeaders.RETRY_AFTER,"ERROR : MESSAGE= "+wse.getMessage())
                        .build(); 
        } catch (Exception ex) {
            logger.error("Webservice Error 2",ex);
            response= Response.status(Response.Status.BAD_GATEWAY)
                        .header(HttpHeaders.RETRY_AFTER,"ERROR : MESSAGE=" +ex.getMessage())
                        .build();
        }
       return response;
    }
}
