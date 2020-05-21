/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.config;

import com.tatsinktech.model.register.Command;
import com.tatsinktech.model.register.Notification_Conf;
import com.tatsinktech.model.register.Product;
import com.tatsinktech.model.register.Request_Conf;
import com.tatsinktech.model.repository.CommandRepository;
import com.tatsinktech.model.repository.Notification_ConfRepository;
import com.tatsinktech.model.repository.ProductRepository;
import com.tatsinktech.model.repository.Request_ConfRepository;
import com.tatsinktechnologic.xml.Application;
import com.tatsinktechnologic.xml.Charging_Api;
import com.tatsinktechnologic.xml.View_Api;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author olivier
 */
public class Load_Configuration implements Serializable {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private Application app_conf;
    private Charging_Api charging_api;
    private View_Api view_api;
    private HashMap<String, Command> SETCOMMAND = new HashMap<String, Command>();
    private HashMap<String, Notification_Conf> SETNOTIFICATION = new HashMap<String, Notification_Conf>();
    private HashMap<String, Product> SETPRODUCT = new HashMap<String, Product>();
    private List<Request_Conf> ListRequest_conf;

    @Autowired
    private CommandRepository commandRepo;

    @Autowired
    private Notification_ConfRepository notifConfRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private Request_ConfRepository requestConfRepo;

    private Load_Configuration() {

        File file_app_listener = new File("etc" + File.separator + "app_config.xml");
        File file_charge_listener = new File("etc" + File.separator + "api_gateway.xml");
        File file_view_listener = new File("etc" + File.separator + "api_viewinfo.xml");

        Serializer serializer_app_listener = new Persister();
        Serializer serializer_charge_listener = new Persister();
        Serializer serializer_view_listener = new Persister();

        try {
            app_conf = serializer_app_listener.read(Application.class, file_app_listener);
            logger.info("successfull load : etc" + File.separator + "app_config.xml");
        } catch (Exception e) {
            logger.error("ERROR in config file http_listener.xml", e);
        }

        try {
            charging_api = serializer_charge_listener.read(Charging_Api.class, file_charge_listener);

            if (charging_api != null) {
                logger.info("successfull load : etc" + File.separator + "api_charging.xml");
            } else {
                logger.error("CANNOT load : etc" + File.separator + "api_charging.xml");
            }

        } catch (Exception e) {
            logger.error("ERROR in config file api_charging.xml", e);
        }

        try {
            view_api = serializer_view_listener.read(View_Api.class, file_view_listener);
            logger.info("successfull load : etc" + File.separator + "api_viewinfo.xml");
        } catch (Exception e) {
            logger.error("ERROR in config file api_viewinfo.xml", e);
        }

        loadNotificationConf();
        loadCommand();
        loadProduct();
        ListRequest_conf = requestConfRepo.findAll();

    }

    private static class SingletonConfig {

        private static final Load_Configuration _loadConf = new Load_Configuration();
    }

    public static Load_Configuration getConfigurationLoader() {
        return SingletonConfig._loadConf;
    }

    public Application getApp_conf() {
        return app_conf;
    }

    public Charging_Api getCharging_api() {
        return charging_api;
    }

    public View_Api getView_api() {
        return view_api;
    }

    public HashMap<String, Command> getSETCOMMAND() {
        return SETCOMMAND;
    }

    public List<Request_Conf> getListRequest_conf() {
        return ListRequest_conf;
    }

    public HashMap<String, Notification_Conf> getSETNOTIFICATION() {
        return SETNOTIFICATION;
    }

    public HashMap<String, Product> getSETPRODUCT() {
        return SETPRODUCT;
    }

    private void loadNotificationConf() {
        List<Notification_Conf> listNotif = notifConfRepo.findAll();
        SETNOTIFICATION.clear();
        for (Notification_Conf notif : listNotif) {
            SETNOTIFICATION.put(notif.getNoficationName(), notif);
        }
    }

    private void loadCommand() {
        List<Command> listCommand = commandRepo.findAll();
        SETCOMMAND.clear();
        for (Command comd : listCommand) {
            SETCOMMAND.put(comd.getCommandName(), comd);
        }
    }

    private void loadProduct() {
        List<Product> listProduct = productRepo.findAll();
        SETPRODUCT.clear();
        for (Product prod : listProduct) {
            SETPRODUCT.put(prod.getProductCode(), prod);
        }
    }

}
