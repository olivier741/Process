/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.process.config;

import com.tatsinktech.process.model.register.Command;
import com.tatsinktech.process.model.register.Notification_Conf;
import com.tatsinktech.process.model.register.Product;
import com.tatsinktech.process.model.register.Request_Conf;
import com.tatsinktech.process.model.repository.CommandRepository;
import com.tatsinktech.process.model.repository.Notification_ConfRepository;
import com.tatsinktech.process.model.repository.ProductRepository;
import com.tatsinktech.process.model.repository.Request_ConfRepository;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaAdmin;

/**
 *
 * @author olivier
 */
@Configuration
@EnableKafka
public class Load_Configuration implements Serializable {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Value("${application.sender.numberThread}")
    private String applicationSenderNumberThread;

    @Value("${application.sender.threadPool}")
    private String applicationSenderThreadPool;

    @Value("${application.sender.sleep-duration}")
    private String applicationSenderSleepDuration;

    @Value("${application.sender.maxQueue}")
    private String applicationSenderMaxQueue;

    @Value("${application.processReg.numberThread}")
    private String applicationProcessRegNumberThread;

    @Value("${application.processReg.threadPool}")
    private String applicationProcessRegThreadPool;

    @Value("${application.processReg.sleep-duration}")
    private String applicationProcessRegSleepDuration;

    @Value("${application.processReg.maxQueue}")
    private String applicationProcessRegMaxQueue;

    @Value("${application.processCheck.numberThread}")
    private String applicationProcessCheckNumberThread;

    @Value("${application.processCheck.threadPool}")
    private String applicationProcessCheckThreadPool;

    @Value("${application.processCheck.sleep-duration}")
    private String applicationProcessCheckSleepDuration;

    @Value("${application.processCheck.maxQueue}")
    private String applicationProcessCheckMaxQueue;

    @Value("${application.processDel.numberThread}")
    private String applicationProcessDelNumberThread;

    @Value("${application.processDel.threadPool}")
    private String applicationProcessDelThreadPool;

    @Value("${application.processDel.sleep-duration}")
    private String applicationProcessDelSleepDuration;

    @Value("${application.processDel.maxQueue}")
    private String applicationProcessDelMaxQueue;

    @Value("${application.processGuide.numberThread}")
    private String applicationProcessGuideNumberThread;

    @Value("${application.processGuide.threadPool}")
    private String applicationProcessGuideThreadPool;

    @Value("${application.processDel.sleep-duration}")
    private String applicationProcessGuideSleepDuration;

    @Value("${application.processGuide.maxQueue}")
    private String applicationProcessGuideMaxQueue;

    @Value("${security.oauth2.client.user-authorization-uri}")
    private String chargingUrl;

    @Value("${security.oauth2.client.access-token-uri}")
    private String chargingUriAuth;

    @Value("${security.oauth2.client.user-authorization-uri}")
    private String chargingUriCharge;

    @Value("${security.oauth2.client.client-id}")
    private String chargingClientName;

    @Value("${charging.user}")
    private String chargingUser;

    @Value("${charging.password}")
    private String chargingPassword;

    @Value("${charging.ws-management}")
    private String chargingWsManagement;

    @Value("${charging.alias.msisdn}")
    private String chargingAliasMsisdn;

    @Value("${charging.alias.amount}")
    private String chargingAliasAmount;

    @Value("${charging.alias.product}")
    private String chargingAliasProduct;

    @Value("${charging.alias.transaction}")
    private String chargingAliasTransaction;

    @Value("${charging.alias.descripition}")
    private String chargingAliasDescription;

    @Value("${viewApi.url}")
    private String viewApiUrl;

    @Value("${viewApi.uri-auth}")
    private String viewApiUriAuth;

    @Value("${viewApi.uri-view}")
    private String viewApiUriView;

    @Value("${viewApi.client-name}")
    private String viewApiClientName;

    @Value("${viewApi.user}")
    private String viewApiUser;

    @Value("${viewApi.password}")
    private String viewApiPassword;

    @Value("${viewApi.ws-management}")
    private String viewApiWsManagement;

    @Value("${viewApi.alias.msisdn}")
    private String viewApiAliasMsisdn;

    @Value("${viewApi.alias.descripition}")
    private String viewApiAliasDescription;

    @Value("${spring.kafka.producer.topic}")
    private String producer_topic;

    @Value("${spring.kafka.consumer.topic}")
    private String consumer_topic;

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String brokerAsString;

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

    @PostConstruct
    private void init() {
        loadNotificationConf();
        loadCommand();
        loadProduct();
        ListRequest_conf = requestConfRepo.findAll();

        logger.info("************** LIST OF POTENTIAL REQUEST **************");
        for (Request_Conf req : ListRequest_conf) {
            logger.info(req.toString());
        }
    }

    public String getApplicationSenderNumberThread() {
        return applicationSenderNumberThread;
    }

    public String getApplicationSenderThreadPool() {
        return applicationSenderThreadPool;
    }

    public String getApplicationSenderSleepDuration() {
        return applicationSenderSleepDuration;
    }

    public String getApplicationSenderMaxQueue() {
        return applicationSenderMaxQueue;
    }

    public String getApplicationProcessRegNumberThread() {
        return applicationProcessRegNumberThread;
    }

    public String getApplicationProcessRegThreadPool() {
        return applicationProcessRegThreadPool;
    }

    public String getApplicationProcessRegSleepDuration() {
        return applicationProcessRegSleepDuration;
    }

    public String getApplicationProcessRegMaxQueue() {
        return applicationProcessRegMaxQueue;
    }

    public String getApplicationProcessCheckNumberThread() {
        return applicationProcessCheckNumberThread;
    }

    public String getApplicationProcessCheckThreadPool() {
        return applicationProcessCheckThreadPool;
    }

    public String getApplicationProcessCheckSleepDuration() {
        return applicationProcessCheckSleepDuration;
    }

    public String getApplicationProcessCheckMaxQueue() {
        return applicationProcessCheckMaxQueue;
    }

    public String getApplicationProcessDelNumberThread() {
        return applicationProcessDelNumberThread;
    }

    public String getApplicationProcessDelThreadPool() {
        return applicationProcessDelThreadPool;
    }

    public String getApplicationProcessDelSleepDuration() {
        return applicationProcessDelSleepDuration;
    }

    public String getApplicationProcessDelMaxQueue() {
        return applicationProcessDelMaxQueue;
    }

    public String getApplicationProcessGuideNumberThread() {
        return applicationProcessGuideNumberThread;
    }

    public String getApplicationProcessGuideThreadPool() {
        return applicationProcessGuideThreadPool;
    }

    public String getApplicationProcessGuideSleepDuration() {
        return applicationProcessGuideSleepDuration;
    }

    public String getApplicationProcessGuideMaxQueue() {
        return applicationProcessGuideMaxQueue;
    }

    public String getChargingUrl() {
        return chargingUrl;
    }

    public String getChargingUriAuth() {
        return chargingUriAuth;
    }

    public String getChargingUriCharge() {
        return chargingUriCharge;
    }

    public String getChargingClientName() {
        return chargingClientName;
    }

    public String getChargingUser() {
        return chargingUser;
    }

    public String getChargingPassword() {
        return chargingPassword;
    }

    public String getChargingWsManagement() {
        return chargingWsManagement;
    }

    public String getChargingAliasMsisdn() {
        return chargingAliasMsisdn;
    }

    public String getChargingAliasAmount() {
        return chargingAliasAmount;
    }

    public String getChargingAliasProduct() {
        return chargingAliasProduct;
    }

    public String getChargingAliasDescription() {
        return chargingAliasDescription;
    }

    public String getViewApiUrl() {
        return viewApiUrl;
    }

    public String getViewApiUriAuth() {
        return viewApiUriAuth;
    }

    public String getViewApiUriView() {
        return viewApiUriView;
    }

    public String getViewApiClientName() {
        return viewApiClientName;
    }

    public String getViewApiUser() {
        return viewApiUser;
    }

    public String getViewApiPassword() {
        return viewApiPassword;
    }

    public String getViewApiWsManagement() {
        return viewApiWsManagement;
    }

    public String getViewApiAliasMsisdn() {
        return viewApiAliasMsisdn;
    }

    public String getViewApiAliasDescription() {
        return viewApiAliasDescription;
    }

    public HashMap<String, Command> getSETCOMMAND() {
        return SETCOMMAND;
    }

    public HashMap<String, Notification_Conf> getSETNOTIFICATION() {
        return SETNOTIFICATION;
    }

    public HashMap<String, Product> getSETPRODUCT() {
        return SETPRODUCT;
    }

    public List<Request_Conf> getListRequest_conf() {
        return ListRequest_conf;
    }

    public CommandRepository getCommandRepo() {
        return commandRepo;
    }

    public Notification_ConfRepository getNotifConfRepo() {
        return notifConfRepo;
    }

    public ProductRepository getProductRepo() {
        return productRepo;
    }

    public Request_ConfRepository getRequestConfRepo() {
        return requestConfRepo;
    }

    public String getChargingAliasTransaction() {
        return chargingAliasTransaction;
    }

    public void setChargingAliasTransaction(String chargingAliasTransaction) {
        this.chargingAliasTransaction = chargingAliasTransaction;
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

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerAsString);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic(producer_topic, 10, (short) 2);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic(consumer_topic, 10, (short) 2);
    }

}
