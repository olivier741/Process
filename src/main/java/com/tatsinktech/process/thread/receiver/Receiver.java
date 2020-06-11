///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tatsinktech.process.thread.receiver;
//
///**
// *
// * @author olivier.tatsinkou
// */
//import com.tatsinktech.process.beans.Process_Request;
//import com.tatsinktech.process.config.Load_Configuration;
//import com.tatsinktech.process.model.register.Action_Type;
//import com.tatsinktech.process.model.register.Command;
//import com.tatsinktech.process.model.register.Mo_Hist;
//import com.tatsinktech.process.model.register.Request_Conf;
//import com.tatsinktech.process.model.repository.Mo_HistRepository;
//import com.tatsinktech.process.thread.register.Process_Check;
//import com.tatsinktech.process.thread.register.Process_Delete;
//import com.tatsinktech.process.thread.register.Process_Guide;
//import com.tatsinktech.process.thread.register.Process_Register;
//import com.tatsinktech.process.thread.sender.Sender;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.sql.Timestamp;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//import java.util.regex.Pattern;
//import javax.annotation.PostConstruct;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.collections.Predicate;
//import org.apache.commons.lang.StringUtils;
//
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.codehaus.jettison.json.JSONException;
//import org.codehaus.jettison.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.annotation.TopicPartition;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Receiver {
//
//    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
//
//    public CountDownLatch countDownLatch0 = new CountDownLatch(3);
//    public CountDownLatch countDownLatch1 = new CountDownLatch(3);
//    public CountDownLatch countDownLatch2 = new CountDownLatch(3);
//    public CountDownLatch countDownLatch3 = new CountDownLatch(3);
//
//    private InetAddress address;
//    private static List<Request_Conf> ListCOMMAND_CONF = null;
//    private static HashMap<String, Command> SETCOMMAND = null;
//
//    @Autowired
//    private Mo_HistRepository mohistRepo;
//
//    @Autowired
//    private Load_Configuration commonConfig;
//
//    @PostConstruct
//    private void init() {
//        ListCOMMAND_CONF = commonConfig.getListRequest_conf();
//        address = gethostName();
//    }
//
//    @KafkaListener(id = "id0", topicPartitions = {
//        @TopicPartition(topic = "${spring.kafka.consumer.topic}", partitions = {"0"})})
//    public void listenPartition0(ConsumerRecord<?, ?> record) {
//        logger.info("Listener Id0, Thread ID: " + Thread.currentThread().getId());
//        String word = String.valueOf(record.value());
//        processReceive(word);
//        countDownLatch0.countDown();
//    }
//
//    @KafkaListener(id = "id1", topicPartitions = {
//        @TopicPartition(topic = "${spring.kafka.consumer.topic}", partitions = {"1"})})
//    public void listenPartition1(ConsumerRecord<?, ?> record) {
//        logger.info("Listener Id1, Thread ID: " + Thread.currentThread().getId());
//        String word = String.valueOf(record.value());
//        processReceive(word);
//        countDownLatch1.countDown();
//    }
//
//    @KafkaListener(id = "id2", topicPartitions = {
//        @TopicPartition(topic = "${spring.kafka.consumer.topic}", partitions = {"2"})})
//    public void listenPartition2(ConsumerRecord<?, ?> record) {
//        logger.info("Listener Id2, Thread ID: " + Thread.currentThread().getId());
//        String word = String.valueOf(record.value());
//        processReceive(word);
//        countDownLatch2.countDown();
//    }
//
////    @KafkaListener(id = "id3", topicPartitions = {
////        @TopicPartition(topic = "${spring.kafka.consumer.topic}", partitions = {"3"})})
////    public void listenPartition3(ConsumerRecord<?, ?> record) {
////        logger.info("Listener Id3, Thread ID: " + Thread.currentThread().getId());
////        String word = String.valueOf(record.value());
////        processReceive(word);
////        countDownLatch3.countDown();
////    }
//
//    private void processReceive(String word) {
//        JSONObject receivedJsonObject = null;
//        try {
//            receivedJsonObject = new JSONObject(word);
//
//            /*
//                    To make sure we successfully deserialized the message to a JSON object, we'll
//                    log the index of JSON object.
//             */
//            logger.info("request recieve : \n" + receivedJsonObject.toString());
//
//            Timestamp receive_time = new Timestamp(System.currentTimeMillis());
//
//            String transaction_id = receivedJsonObject.getString("transaction_id");
//            String content = receivedJsonObject.getString("content");
//            String receiver = receivedJsonObject.getString("receiver");
//            String msisdn = receivedJsonObject.getString("sender");
//            String exchange_mode = receivedJsonObject.getString("exchange_mode");
//            String service_name = receivedJsonObject.getString("service_id");
//
//            List<Request_Conf> listCommand_conf = getCheck_CommandConf(content);
//
//            logger.info("transaction_id   : " + transaction_id);
//            logger.info("content send by customer   : " + content);
//            logger.info("customer phone number      : " + msisdn);
//            logger.info("short code                 : " + receiver);
//
//            if (listCommand_conf.size() > 0) {
//                // good syntax send by customer
//                Request_Conf current_cmd_conf = listCommand_conf.get(0);
//                Process_Request process_req = new Process_Request();
//
//                process_req.setActionName(current_cmd_conf.getActionName());
//                process_req.setActionType(current_cmd_conf.getActionType());
//                process_req.setCommanCode(current_cmd_conf.getCommandCode());
//                process_req.setCommandName(current_cmd_conf.getCommandName());
//                process_req.setContent(content);
//                process_req.setExchangeMode(exchange_mode);
//                process_req.setLanguage("");
//                process_req.setMsisdn(msisdn);
//                process_req.setNotificationCode("");
//                process_req.setParamLength(current_cmd_conf.getParamLength());
//                process_req.setParamName(current_cmd_conf.getParamName());
//                process_req.setParamPattern(current_cmd_conf.getParamPattern());
//                process_req.setProductCode(current_cmd_conf.getProductCode());
//                process_req.setRcvChannel(current_cmd_conf.getReceiveChannel());
//                process_req.setReceiveTime(receive_time);
//                process_req.setReceiver(receiver);
//                process_req.setSendChannel(current_cmd_conf.getSendChannel());
//                process_req.setSplitSeparate(current_cmd_conf.getSplitSeparator());
//                process_req.setTransaction_id(transaction_id);
//                process_req.setServiceName(current_cmd_conf.getServiceName());
//
//                String split_sep = "\\s+";
//                if (current_cmd_conf.getSplitSeparator() != null) {
//                    split_sep = current_cmd_conf.getSplitSeparator();
//                }
//                List<String> listCommand = Arrays.asList(content.split(split_sep));
//
//                String param_value = "";
//                for (int i = 1; i < listCommand.size(); i++) {
//                    //check parameter 
//                    param_value += listCommand.get(i) + split_sep;
//
//                }
//                process_req.setParamValue(param_value.toUpperCase().trim());
//
//                if (current_cmd_conf.getActionType() != null) {
//
//                    Action_Type action_type = current_cmd_conf.getActionType();
//                    switch (action_type) {
//                        case REGISTER:
//                            Process_Register.addMo_Queue(process_req);
//                            logger.info("Emitte to Register Process : " + process_req);
//                            break;
//                        case CHECK:
//                            Process_Check.addMo_Queue(process_req);
//                            logger.info("Emitte to Check Process : " + process_req);
//                            break;
//                        case DELETE:
//                            Process_Delete.addMo_Queue(process_req);
//                            logger.info("Emitte to Delete Process : " + process_req);
//                            break;
//                        case GUIDE:
//                            Process_Guide.addMo_Queue(process_req);
//                            logger.info("Emitte to Guide Process : " + process_req);
//                            break;
//                        case LIST:
////                                Process_ListReg.addMo_Queue(process_req);
//                            logger.info("Emitte to List Registration : " + process_req);
//                            break;
////                    case ACC_CHANGE_ALIAS:
////                        Process_ChangeAlias.addMo_Queue(process_req);
////                        logger.info("Emitte to Change Alias : " + process_req);
////                        break;
////                    case ACC_ADD_CHATGROUP:
////                        Process_Add_ChatGroup.addMo_Queue(process_req);
////                        logger.info("Emitte to Change Alias : " + process_req);
////                        break;
////                    case ACC_DEL_CHATGROUP:
////
////                    case ACC_LIST_ALL_CHATGROUP:
////                    case ACC_LIST_REG_CHATGROUP:
////                    case ACC_LIST_NOTREG_CHATGROUP:
////                        Process_AccountMng.addMo_Queue(process_req);
////                        logger.info("Emitte to List Registration : " + process_req);
////                        break;
//                        default:
//                            //action not existe
//                            process_req.setNotificationCode("ACTION-NOT-DEFINE");
//
//                            // send to sender
//                            Sender.addMo_Queue(process_req);
//
//                            Command cmd = SETCOMMAND.get(process_req.getCommandName());
//                            String commandCode = null;
//                            String commandName = null;
//                            if (cmd != null) {
//                                commandCode = cmd.getCommandCode();
//                                commandName = cmd.getCommandName();
//                            }
//                            Mo_Hist mo_hist = new Mo_Hist();
//                            mo_hist.setActionType(process_req.getActionType());
//                            mo_hist.setChannel(process_req.getSendChannel());
//                            mo_hist.setContent(content);
//                            mo_hist.setMsisdn(msisdn);
//                            mo_hist.setReceiveTime(receive_time);
//                            mo_hist.setCommandCode(commandCode);
//                            mo_hist.setCommandName(commandName);
//                            mo_hist.setTransactionId(transaction_id);
//                            mo_hist.setProcessUnit("Receiver");
//                            mo_hist.setIpAddress(address.getHostName() + "@" + address.getHostAddress());
//                            mo_hist.setDescription("ACTION NOT DEFINE");
//                            mo_hist.setExchangeMode(exchange_mode);
//
//                            mohistRepo.save(mo_hist);
//
//                            logger.info("insert into mo_his ");
//
//                            break;
//
//                    }
//
//                } else {
//                    process_req.setNotificationCode("RECEIVER-NOT-ACTION-TYPE");
//                    process_req.setReceiveTime(receive_time);
//                    // send to sender
//                    Sender.addMo_Queue(process_req);
//
//                    Command cmd = SETCOMMAND.get(process_req.getCommandName());
//
//                    String commandCode = null;
//                    String commandName = null;
//                    if (cmd != null) {
//                        commandCode = cmd.getCommandCode();
//                        commandName = cmd.getCommandName();
//                    }
//
//                    Mo_Hist mo_hist = new Mo_Hist();
//                    mo_hist.setActionType(process_req.getActionType());
//                    mo_hist.setChannel(process_req.getSendChannel());
//                    mo_hist.setContent(content);
//                    mo_hist.setMsisdn(msisdn);
//                    mo_hist.setReceiveTime(receive_time);
//                    mo_hist.setTransactionId(transaction_id);
//                    mo_hist.setCommandCode(commandCode);
//                    mo_hist.setCommandName(commandName);
//                    mo_hist.setProcessUnit("Receiver");
//                    mo_hist.setIpAddress(address.getHostName() + "@" + address.getHostAddress());
//                    mo_hist.setDescription("NOT ACTION TYPE");
//                    mo_hist.setExchangeMode(exchange_mode);
//
//                    mohistRepo.save(mo_hist);
//
//                    logger.info("insert into mo_his ");
//                }
//
//            } else {
//                // wrong syntax send by customer
//                Process_Request process_req = new Process_Request();
//                process_req.setTransaction_id(transaction_id);
//                process_req.setReceiver(receiver);
//                process_req.setMsisdn(msisdn);
//                process_req.setReceiveTime(receive_time);
//                process_req.setContent(content);
//                process_req.setNotificationCode("RECEIVER-WRONG-SYNTAX");
//
//                // send to sender
//                Sender.addMo_Queue(process_req);
//
//                Mo_Hist mo_hist = new Mo_Hist();
//                mo_hist.setActionType(process_req.getActionType());
//                mo_hist.setChannel(process_req.getSendChannel());
//                mo_hist.setContent(content);
//
//                mo_hist.setMsisdn(msisdn);
//                mo_hist.setReceiveTime(receive_time);
//                mo_hist.setTransactionId(transaction_id);
//                mo_hist.setProcessUnit("Receiver");
//                mo_hist.setIpAddress(address.getHostName() + "@" + address.getHostAddress());
//                mo_hist.setDescription("WRONG SYNTAX");
//                mo_hist.setExchangeMode(exchange_mode);
//
//                mohistRepo.save(mo_hist);
//
//                logger.info("insert into mo_his ");
//
//            }
//        } catch (JSONException e) {
//            logger.error(e.getMessage());
//        }
//    }
//
//    private static List<Request_Conf> getCheck_CommandConf(final String command) {
//
//        List<Request_Conf> result = new ArrayList<Request_Conf>();
//
//        for (Request_Conf cmd_conf : ListCOMMAND_CONF) {
//            result.add(cmd_conf);
//        }
//
//        CollectionUtils.filter(result, new Predicate() {
//
//            @Override
//            public boolean evaluate(Object o) {
//
//                Integer check_lengh_param;
//                String check_param_pattern = null;
//                String check_cmd = null;
//                String separator = null;
//
//                // get separator from database. if not separator, get space as default
//                separator = ((Request_Conf) o).getSplitSeparator();
//                if (StringUtils.isBlank(separator)) {
//                    separator = "\\s+";
//                }
//
//                // get number of parameter from database
//                check_lengh_param = ((Request_Conf) o).getParamLength();
//                if (check_lengh_param == null) {
//                    check_lengh_param = 0;
//                }
//
//                // get regex String of parameter from database
//                check_param_pattern = ((Request_Conf) o).getParamPattern();
//                if (StringUtils.isBlank(check_param_pattern)) {
//                    check_param_pattern = "";
//                }
//
//                // get command from database
//                check_cmd = ((Request_Conf) o).getCommandCode().toUpperCase().trim();
//
//                // build regex pattern to check parameter
//                Pattern pattern_param = Pattern.compile(check_param_pattern);
//
//                // build regex pattern to check separator between command or parameter
//                Pattern pattern_separator = Pattern.compile(separator);
//
//                // get list command + param send by customer
//                List<String> listCommand = Arrays.asList(pattern_separator.split(command));
//
//                //check number of parameter 
//                boolean match_lenght = true;
//                if (check_lengh_param != 0) {
//                    match_lenght = (listCommand.size() - 1) == check_lengh_param;
//                }
//
//                //check command
//                boolean match_cmd = check_cmd.equals(listCommand.get(0).toUpperCase().trim());
//
//                boolean match_param = true;
//
//                if (check_lengh_param > 0 && match_lenght) {
//                    // check separator                    
//                    for (int i = 1; i < listCommand.size(); i++) {
//                        //check parameter 
//                        match_param = match_param && pattern_param.matcher(listCommand.get(i).toUpperCase().trim()).find();
//                    }
//
//                }
//
//                return match_cmd && match_lenght && match_param;
//            }
//
//        });
//
//        return result;
//    }
//
//    private InetAddress gethostName() {
//
//        InetAddress addr = null;
//
//        try {
//            addr = InetAddress.getLocalHost();
//        } catch (UnknownHostException ex) {
//            logger.error("Hostname can not be resolved", ex);
//        }
//        return addr;
//    }
//
//    public String convertDate_String(Timestamp date, String format) {
//
//        // DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//        DateFormat dateFormat = new SimpleDateFormat(format);
//
//        //to convert Date to String, use format method of SimpleDateFormat class.
//        String strDate = dateFormat.format(date);
//
//        return strDate;
//    }
//}
