package com.tatsinktech.process.kafka.service;

import com.tatsinktech.process.model.register.Action_Type;
import com.tatsinktech.process.model.register.Command;
import com.tatsinktech.process.model.register.Mo_Hist;
import com.tatsinktech.process.model.register.Request_Conf;
import com.tatsinktech.process.model.repository.Mo_HistRepository;
import com.tatsinktech.process.beans.Message_Exchg;
import com.tatsinktech.process.beans.Process_Request;
import com.tatsinktech.process.config.Load_Configuration;
import com.tatsinktech.process.thread.register.Process_Check;
import com.tatsinktech.process.thread.register.Process_Delete;
import com.tatsinktech.process.thread.register.Process_Guide;
import com.tatsinktech.process.thread.register.Process_Register;
import com.tatsinktech.process.thread.sender.Sender;
import com.tatsinktech.process.util.ConverterXML_JSON;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Component
public class MyKafkaConsumer {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public CountDownLatch countDownLatch0 = new CountDownLatch(3);
    public CountDownLatch countDownLatch1 = new CountDownLatch(3);
    public CountDownLatch countDownLatch2 = new CountDownLatch(3);
    public CountDownLatch countDownLatch3 = new CountDownLatch(3);

    private InetAddress address;
    private static List<Request_Conf> ListCOMMAND_CONF = null;
    private static HashMap<String, Command> SETCOMMAND = null;

    @Autowired
    private Mo_HistRepository mohistRepo;

    @Autowired
    private Load_Configuration commonConfig;

    @PostConstruct
    private void init() {
        ListCOMMAND_CONF = commonConfig.getListRequest_conf();
        address = gethostName();
    }

    @KafkaListener(topics = "${spring.kafka.consumer.topic}")
    public void listenPartition0(ConsumerRecord<?, ?> record) {
        logger.info("Listener Id1, Thread ID: " + Thread.currentThread().getId());
        String word = String.valueOf(record.value());
        processReceive(word, "listenPartition0");
        countDownLatch0.countDown();
    }

    @KafkaListener(topics = "${spring.kafka.consumer.topic}")
    public void listenPartition1(ConsumerRecord<?, ?> record) {
        logger.info("Listener Id2, Thread ID: " + Thread.currentThread().getId());
        String word = String.valueOf(record.value());
        processReceive(word, "listenPartition2");
        countDownLatch0.countDown();
    }

    @KafkaListener(topics = "${spring.kafka.consumer.topic}")
    public void listenPartition2(ConsumerRecord<?, ?> record) {
        logger.info("Listener Id3, Thread ID: " + Thread.currentThread().getId());
        String word = String.valueOf(record.value());
        processReceive(word, "listenPartition3");
        countDownLatch0.countDown();
    }

    @KafkaListener(topics = "${spring.kafka.consumer.topic}")
    public void listenPartition3(ConsumerRecord<?, ?> record) {
        logger.info("Listener Id4, Thread ID: " + Thread.currentThread().getId());
        String word = String.valueOf(record.value());
        processReceive(word, "listenPartition4");
        countDownLatch0.countDown();
    }

    @KafkaListener(topics = "${spring.kafka.consumer.topic}")
    public void listenPartition4(ConsumerRecord<?, ?> record) {
        logger.info("Listener Id5, Thread ID: " + Thread.currentThread().getId());
        String word = String.valueOf(record.value());
        processReceive(word, "listenPartition5");
        countDownLatch0.countDown();
    }

    private void processReceive(String word, String listenPartition) {
        JSONObject receivedJsonObject = null;
        try {
            receivedJsonObject = new JSONObject(word);

            /*
                    To make sure we successfully deserialized the message to a JSON object, we'll
                    log the index of JSON object.
             */
            logger.info(listenPartition + " -- request recieve  -- " + receivedJsonObject.toString());

            Timestamp receive_time = new Timestamp(System.currentTimeMillis());

            String transaction_id = receivedJsonObject.getString("transaction_id");
            String content = receivedJsonObject.getString("content");
            String receiver = receivedJsonObject.getString("receiver");
            String msisdn = receivedJsonObject.getString("sender");
            String exchange_mode = receivedJsonObject.getString("exchange_mode");
            String service_name_gw = receivedJsonObject.getString("service_id");

            Process_Request process_req = new Process_Request();

            process_req.setContent(content);
            process_req.setExchangeMode(exchange_mode);
            process_req.setLanguage("");
            process_req.setMsisdn(msisdn);
            process_req.setNotificationCode("");
            process_req.setReceiveTime(receive_time);
            process_req.setReceiver(receiver);
            process_req.setTransaction_id(transaction_id);
            process_req.setRcvChannel(receiver);
            process_req.setSendChannel(receiver);

            List<Request_Conf> listCommand_conf = getCheck_CommandConf(content, service_name_gw,receiver);

            logger.info(listenPartition + "-- transaction_id   : " + transaction_id);
            logger.info(listenPartition + " -- content send by customer   : " + content);
            logger.info(listenPartition + " -- customer phone number      : " + msisdn);
            logger.info(listenPartition + " -- short code                 : " + receiver);

            if (listCommand_conf!= null && !listCommand_conf.isEmpty()) {
                // good syntax send by customer
                Request_Conf current_cmd_conf = listCommand_conf.get(0);

                process_req.setActionName(current_cmd_conf.getActionName());
                process_req.setActionType(current_cmd_conf.getActionType());
                process_req.setCommanCode(current_cmd_conf.getCommandCode());
                process_req.setCommandName(current_cmd_conf.getCommandName());
                process_req.setParamLength(current_cmd_conf.getParamLength());
                process_req.setParamName(current_cmd_conf.getParamName());
                process_req.setParamPattern(current_cmd_conf.getParamPattern());
                process_req.setProductCode(current_cmd_conf.getProductCode());
                process_req.setRcvChannel(current_cmd_conf.getReceiveChannel());
                process_req.setSendChannel(current_cmd_conf.getSendChannel());
                process_req.setSplitSeparate(current_cmd_conf.getSplitSeparator());
                process_req.setServiceName(current_cmd_conf.getServiceName());

                String split_sep = "\\s+";
                if (current_cmd_conf.getSplitSeparator() != null) {
                    split_sep = current_cmd_conf.getSplitSeparator();
                }
                List<String> listCommand = Arrays.asList(content.split(split_sep));

                String param_value = "";
                for (int i = 1; i < listCommand.size(); i++) {
                    //check parameter 
                    param_value += listCommand.get(i) + split_sep;

                }
                process_req.setParamValue(param_value.toUpperCase().trim());

                if (current_cmd_conf.getActionType() != null) {

                    Action_Type action_type = current_cmd_conf.getActionType();
                    switch (action_type) {
                        case REGISTER:
                            Process_Register.addMo_Queue(process_req);
                            logger.info(listenPartition + " -- Emitte to Register Process : " + process_req);
                            break;
                        case CHECK:
                            Process_Check.addMo_Queue(process_req);
                            logger.info(listenPartition + " -- Emitte to Check Process : " + process_req);
                            break;
                        case DELETE:
                            Process_Delete.addMo_Queue(process_req);
                            logger.info(listenPartition + " -- Emitte to Delete Process : " + process_req);
                            break;
                        case GUIDE:
                            Process_Guide.addMo_Queue(process_req);
                            logger.info(listenPartition + " -- Emitte to Guide Process : " + process_req);
                            break;
                        case LIST:
//                                Process_ListReg.addMo_Queue(process_req);
                            logger.info(listenPartition + " -- Emitte to List Registration : " + process_req);
                            break;
//                    case ACC_CHANGE_ALIAS:
//                        Process_ChangeAlias.addMo_Queue(process_req);
//                        logger.info("Emitte to Change Alias : " + process_req);
//                        break;
//                    case ACC_ADD_CHATGROUP:
//                        Process_Add_ChatGroup.addMo_Queue(process_req);
//                        logger.info("Emitte to Change Alias : " + process_req);
//                        break;
//                    case ACC_DEL_CHATGROUP:
//
//                    case ACC_LIST_ALL_CHATGROUP:
//                    case ACC_LIST_REG_CHATGROUP:
//                    case ACC_LIST_NOTREG_CHATGROUP:
//                        Process_AccountMng.addMo_Queue(process_req);
//                        logger.info("Emitte to List Registration : " + process_req);
//                        break;
                        default:
                            //action not existe
                            process_req.setNotificationCode("ACTION-NOT-DEFINE");

                            // send to sender
                            Sender.addMo_Queue(process_req);

                            Command cmd = SETCOMMAND.get(process_req.getCommandName());
                            String commandCode = null;
                            String commandName = null;
                            if (cmd != null) {
                                commandCode = cmd.getCommandCode();
                                commandName = cmd.getCommandName();
                            }
                            Mo_Hist mo_hist = new Mo_Hist();
                            mo_hist.setActionType(process_req.getActionType());
                            mo_hist.setChannel(process_req.getSendChannel());
                            mo_hist.setContent(content);
                            mo_hist.setMsisdn(msisdn);
                            mo_hist.setReceiveTime(receive_time);
                            mo_hist.setCommandCode(commandCode);
                            mo_hist.setCommandName(commandName);
                            mo_hist.setTransactionId(transaction_id);
                            mo_hist.setProcessUnit("Receiver");
                            mo_hist.setIpAddress(address.getHostName() + "@" + address.getHostAddress());
                            mo_hist.setDescription("ACTION NOT DEFINE");
                            mo_hist.setExchangeMode(exchange_mode);

                            mohistRepo.save(mo_hist);

                            logger.info(listenPartition + " -- insert into mo_his ");

                            break;

                    }

                } else {
                    process_req.setNotificationCode("RECEIVER-NOT-ACTION-TYPE");
                    process_req.setReceiveTime(receive_time);
                    // send to sender
                    Sender.addMo_Queue(process_req);

                    Command cmd = SETCOMMAND.get(process_req.getCommandName());

                    String commandCode = null;
                    String commandName = null;
                    if (cmd != null) {
                        commandCode = cmd.getCommandCode();
                        commandName = cmd.getCommandName();
                    }

                    Mo_Hist mo_hist = new Mo_Hist();
                    mo_hist.setActionType(process_req.getActionType());
                    mo_hist.setChannel(process_req.getSendChannel());
                    mo_hist.setContent(content);
                    mo_hist.setMsisdn(msisdn);
                    mo_hist.setReceiveTime(receive_time);
                    mo_hist.setTransactionId(transaction_id);
                    mo_hist.setCommandCode(commandCode);
                    mo_hist.setCommandName(commandName);
                    mo_hist.setProcessUnit("Receiver");
                    mo_hist.setIpAddress(address.getHostName() + "@" + address.getHostAddress());
                    mo_hist.setDescription("NOT ACTION TYPE");
                    mo_hist.setExchangeMode(exchange_mode);

                    mohistRepo.save(mo_hist);

                    logger.info(listenPartition + " -- insert into mo_his ");
                }

            } else {
                // wrong syntax send by customer
                process_req.setNotificationCode("RECEIVER-WRONG-SYNTAX");

                // send to sender
                Sender.addMo_Queue(process_req);

                Mo_Hist mo_hist = new Mo_Hist();
                mo_hist.setActionType(process_req.getActionType());
                mo_hist.setChannel(process_req.getSendChannel());
                mo_hist.setContent(content);

                mo_hist.setMsisdn(msisdn);
                mo_hist.setReceiveTime(receive_time);
                mo_hist.setTransactionId(transaction_id);
                mo_hist.setProcessUnit("Receiver");
                mo_hist.setIpAddress(address.getHostName() + "@" + address.getHostAddress());
                mo_hist.setDescription("WRONG SYNTAX");
                mo_hist.setExchangeMode(exchange_mode);

                mohistRepo.save(mo_hist);

                logger.info(listenPartition + " -- insert into mo_his ");

            }
        } catch (JSONException e) {
            logger.error(e.getMessage());
        }
    }

    private static List<Request_Conf> getCheck_CommandConf(final String command, final String service_gw,final String channel) {

        List<Request_Conf> result = new ArrayList<Request_Conf>();

        for (Request_Conf cmd_conf : ListCOMMAND_CONF) {
            result.add(cmd_conf);
        }

        CollectionUtils.filter(result, new Predicate() {
            Logger logger = LoggerFactory.getLogger(this.getClass().getName());

            @Override
            public boolean evaluate(Object o) {

                Integer check_lengh_param;
                String check_param_pattern = null;
                String check_cmd = null;
                String separator = null;
                String service = null;
                String receiveChannel = null;

                // get separator from database. if not separator, get space as default
                separator = ((Request_Conf) o).getSplitSeparator();
                if (StringUtils.isBlank(separator)) {
                    separator = "\\s+";
                }

                // get number of parameter from database
                check_lengh_param = ((Request_Conf) o).getParamLength();
                if (check_lengh_param == null) {
                    check_lengh_param = 0;
                }

                // get regex String of parameter from database
                check_param_pattern = ((Request_Conf) o).getParamPattern();
                if (StringUtils.isBlank(check_param_pattern)) {
                    check_param_pattern = "";
                }

                // get service name from database
                service = ((Request_Conf) o).getServiceName();
                
                receiveChannel = ((Request_Conf) o).getReceiveChannel();
                if (StringUtils.isBlank(receiveChannel)){
                    receiveChannel = "\\w";
                }

                // get command from database
                check_cmd = ((Request_Conf) o).getCommandCode().toUpperCase().trim();

                // build regex pattern to check parameter
                Pattern pattern_param = Pattern.compile(check_param_pattern);

                // build regex pattern to check separator between command or parameter
                Pattern pattern_separator = Pattern.compile(separator);

                // build regex pattern to check Service Name 
                Pattern pattern_service = Pattern.compile(service_gw);
                
                // build regex pattern to check ReceiveChannel of service
                Pattern pattern_rcvChannel = Pattern.compile(receiveChannel);

                // get list command + param send by customer
                List<String> listCommand = Arrays.asList(pattern_separator.split(command));

                //check number of parameter 
                int numbParam = listCommand.size() - 1;
                boolean match_lenght = numbParam == check_lengh_param;

                //check command
                boolean match_cmd = check_cmd.equals(listCommand.get(0).toUpperCase().trim());

                //check service
                boolean match_service = false;
                if (StringUtils.isBlank(service) && StringUtils.isBlank(service_gw)) {
                    match_service = true;
                } else if (!StringUtils.isBlank(service) && !StringUtils.isBlank(service_gw)) {
                    match_service = pattern_service.matcher(service.toUpperCase().trim()).find();
                }
                
                 //check service
                boolean match_rcvChannel =  pattern_rcvChannel.matcher(channel.toUpperCase().trim()).find();
               

                String param_chain = "";
                for (int i = 1; i < listCommand.size(); i++) {
                    //check parameter 
                    if (i != 1) {
                        param_chain = param_chain + " | " + listCommand.get(i).toUpperCase().trim();
                    } else {
                        param_chain = listCommand.get(i).toUpperCase().trim();
                    }
                }

                boolean match_param = false;
                if (check_lengh_param >= 0 && match_lenght) {
                    // check separator       

                    if (check_lengh_param == 0) {
                        match_param = true;
                    } else {
                        for (int i = 1; i < listCommand.size(); i++) {
                            //check parameter 
                            match_param = match_param && pattern_param.matcher(listCommand.get(i).toUpperCase().trim()).find();
                        }

                    }
                }

                logger.info("########################### CHECKING SYNTAX ###########################");

                if (match_cmd) {
                    logger.info("SYNTAX MATCH -- reques: " + listCommand.get(0) + " -- command : " + check_cmd);
                } else {
                    logger.info("SYNTAX NOT MATCH -- reques: " + listCommand.get(0) + " -- command : " + check_cmd);
                }

                if (match_lenght) {
                    logger.info("SYNTAX MATCH -- Number Parameter: " + numbParam + " -- lengh_param : " + check_lengh_param);
                } else {
                    logger.info("SYNTAX NOT MATCH -- Number Parameter: " + numbParam + " -- lengh_param : " + check_lengh_param);
                }

                if (match_param) {
                    logger.info("SYNTAX MATCH -- Parameters: " + param_chain + " -- regex : " + check_param_pattern);
                } else {
                    logger.info("SYNTAX NOT MATCH -- Parameters: " + param_chain + " -- regex : " + check_param_pattern);
                }

                if (match_service) {
                    logger.info("SYNTAX MATCH -- regex service: " + service_gw + " -- service : " + service);
                } else {
                    logger.info("SYNTAX NOT MATCH -- regex service: " + service_gw + " -- service : " + service);
                }

                
                 if (match_rcvChannel) {
                    logger.info("SYNTAX MATCH -- Short Code : " + channel + " -- channel : " + receiveChannel);
                } else {
                    logger.info("SYNTAX NOT MATCH -- Short Code : " + channel + " -- channel : " + receiveChannel);
                }
                 
                return match_cmd && match_lenght && match_param && match_service && match_rcvChannel;
            }

        });

        return result;
    }

    private InetAddress gethostName() {

        InetAddress addr = null;

        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            logger.error("Hostname can not be resolved", ex);
        }
        return addr;
    }

    public String convertDate_String(Timestamp date, String format) {

        // DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat(format);

        //to convert Date to String, use format method of SimpleDateFormat class.
        String strDate = dateFormat.format(date);

        return strDate;
    }
}
