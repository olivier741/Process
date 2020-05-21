/*
 * Copyright 2019 olivier.tatsinkou.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tatsinktech;

import com.tatsinktech.beans.Process_Request;
import com.tatsinktech.config.Load_Configuration;
import com.tatsinktechnologic.process.Process_Register;
import com.tatsinktech.thread.sender.Sender;
import com.tatsinktech.util.Utils;
import com.tatsinktechnologic.xml.Application;
import com.tatsinktechnologic.xml.Kafka_Conf;
import com.tatsinktechnologic.xml.ProcessThread_Check;
import com.tatsinktechnologic.xml.ProcessThread_Del;
import com.tatsinktechnologic.xml.ProcessThread_Guide;
import com.tatsinktechnologic.xml.ProcessThread_Reg;
import com.tatsinktechnologic.xml.ReceiverThread_Conf;
import com.tatsinktechnologic.xml.SenderThread_Conf;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * @author olivier.tatsinkou
 */
public class ApplicationProcess {

    /**
     * @param args the command line arguments
     */
    private static Logger logger = LoggerFactory.getLogger(ApplicationProcess.class);

    private static EntityManagerFactory emf;
    private static SenderThread_Conf sendthread_conf;
    private static ReceiverThread_Conf receiverthead_conf;
    private static ProcessThread_Reg processthead_reg;
    private static ProcessThread_Del processthead_del;
    private static ProcessThread_Guide processthead_guide;
    private static ProcessThread_Check processthead_check;
    private static Kafka_Conf kafka_conf;
    private static Application app_conf;
    private static BlockingQueue<Process_Request> send_queue;
    private static BlockingQueue<Process_Request> reg_queue;
    private static BlockingQueue<Process_Request> del_queue;
    private static BlockingQueue<Process_Request> guide_queue;
    private static BlockingQueue<Process_Request> check_queue;

    private static Load_Configuration commonConfig = Load_Configuration.getConfigurationLoader();

    public static void main(String[] args) {
        // TODO code application logic here

        ConfigurableApplicationContext ConfAppContext = SpringApplication.run(ApplicationProcess.class, args);

        List<Runnable> process_runnables = new ArrayList<Runnable>();

//        // process reg
//        for (int i = 0; i < 10; i++) {
//            PrintThread printThread1 = (PrintThread) ConfAppContext.getBean("printThread");
//            printThread1.setName("Thread " + i);
//            process_runnables.add(printThread1);
//        }
//        ExecutorService process_Execute_reg = Executors.newFixedThreadPool(10);
//        PrintThread.executeRunnables(process_Execute_reg, process_runnables);
//
//        PropertyConfigurator.configure("etc" + File.separator + "log4j.cfg");
//        logger.info("Load log4j config file done.");

        app_conf = commonConfig.getApp_conf();

        sendthread_conf = app_conf.getSender_thread();
        receiverthead_conf = app_conf.getReceiver_thread();
        processthead_reg = app_conf.getProcess_thread_reg();
        processthead_del = app_conf.getProcess_thread_del();
        processthead_guide = app_conf.getProcess_thread_guide();
        processthead_check = app_conf.getProcess_thread_check();
        kafka_conf = app_conf.getKafka_conf();

        int numberRecord = kafka_conf.getNumberRecord();
        String consum_topic = kafka_conf.getConsumer_topic();
        String prod_topic = kafka_conf.getProducer_topic();

        int send_thread_num = sendthread_conf.getNumberThread();
        int send_thread_pool = sendthread_conf.getThreadPool();
        int send_sleep_duration = sendthread_conf.getSleep_duration();
        int send_maxQueue = sendthread_conf.getMaxQueue();

        int process_reg_num = processthead_reg.getNumberThread();
        int process_reg_pool = processthead_reg.getThreadPool();
        int process_reg_sleep_duration = processthead_reg.getSleep_duration();
        int process_reg_maxQueue = processthead_reg.getMaxQueue();

        int process_check_num = processthead_check.getNumberThread();
        int process_check_pool = processthead_check.getThreadPool();
        int process_check_sleep_duration = processthead_check.getSleep_duration();
        int process_check_maxQueue = processthead_check.getMaxQueue();

        int process_del_num = processthead_del.getNumberThread();
        int process_del_pool = processthead_del.getThreadPool();
        int process_del_sleep_duration = processthead_del.getSleep_duration();
        int process_del_maxQueue = processthead_del.getMaxQueue();

        int process_guide_num = processthead_guide.getNumberThread();
        int process_guide_pool = processthead_guide.getThreadPool();
        int process_guide_sleep_duration = processthead_guide.getSleep_duration();
        int process_guide_maxQueue = processthead_guide.getMaxQueue();

        send_queue = new ArrayBlockingQueue<>(send_maxQueue);
        reg_queue = new ArrayBlockingQueue<>(process_reg_maxQueue);
        del_queue = new ArrayBlockingQueue<>(process_del_maxQueue);
        check_queue = new ArrayBlockingQueue<>(process_check_maxQueue);
        guide_queue = new ArrayBlockingQueue<>(process_guide_maxQueue);

        List<Runnable> receiver_runnables = new ArrayList<Runnable>();
        List<Runnable> sender_runnables = new ArrayList<Runnable>();
        List<Runnable> process_reg_runnables = new ArrayList<Runnable>();
        List<Runnable> process_del_runnables = new ArrayList<Runnable>();
        List<Runnable> process_check_runnables = new ArrayList<Runnable>();
        List<Runnable> process_guide_runnables = new ArrayList<Runnable>();

        // sender
        for (int i = 0; i < send_thread_num; i++) {
            Sender senderThread = (Sender) ConfAppContext.getBean("SenderThread");
            senderThread.setSleep_duration(send_sleep_duration);
            sender_runnables.add(senderThread);
        }
        Sender.setSend_queue(send_queue);
        ExecutorService send_Execute = Executors.newFixedThreadPool(send_thread_pool);
        Sender.executeRunnables(send_Execute, sender_runnables);

        // process reg
        for (int i = 0; i < process_reg_num; i++) {
            Process_Register regProcessThread = (Process_Register) ConfAppContext.getBean("RegisterProcessThread");
            process_reg_runnables.add(regProcessThread);
        }
        Process_Register.loadFeatures(send_sleep_duration, reg_queue);
        ExecutorService process_Execute_reg = Executors.newFixedThreadPool(process_reg_pool);
        Process_Register.executeRunnables(process_Execute_reg, process_reg_runnables);

//        // process check
//        for (int i = 0; i < process_check_num; i++) {
//            process_check_runnables.add(new Process_Check(emf, check_queue, process_check_sleep_duration));
//        }
//        ExecutorService process_Execute_check = Executors.newFixedThreadPool(process_check_pool);
//        Process_Check.executeRunnables(process_Execute_check, process_check_runnables);
//
//        // process Delete
//        for (int i = 0; i < process_del_num; i++) {
//            process_del_runnables.add(new Process_Delete(emf, del_queue, process_del_sleep_duration));
//        }
//        ExecutorService process_Execute_del = Executors.newFixedThreadPool(process_del_pool);
//        Process_Delete.executeRunnables(process_Execute_del, process_del_runnables);
//
//        // process Guide
//        for (int i = 0; i < process_guide_num; i++) {
//            process_guide_runnables.add(new Process_Guide(emf, guide_queue, process_guide_sleep_duration));
//        }
//        ExecutorService process_Execute_guide = Executors.newFixedThreadPool(process_guide_pool);
//        Process_Guide.executeRunnables(process_Execute_guide, process_guide_runnables);
    }

    private static InetAddress gethostName() {

        InetAddress addr = null;

        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            logger.error("Hostname can not be resolved", ex);
        }
        return addr;
    }

}
