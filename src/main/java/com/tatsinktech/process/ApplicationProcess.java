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
package com.tatsinktech.process;

import com.tatsinktech.process.beans.Process_Request;
import com.tatsinktech.process.config.Load_Configuration;
import com.tatsinktech.process.thread.register.Process_Register;
import com.tatsinktech.process.thread.sender.Sender;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * @author olivier.tatsinkou
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class ApplicationProcess {

    /**
     * @param args the command line arguments
     */
//    private static Logger logger = LoggerFactory.getLogger(ApplicationProcess.class);
//
//    private static BlockingQueue<Process_Request> send_queue;
//    private static BlockingQueue<Process_Request> reg_queue;
//    private static BlockingQueue<Process_Request> del_queue;
//    private static BlockingQueue<Process_Request> guide_queue;
//    private static BlockingQueue<Process_Request> check_queue;
//
//    @Autowired
//    private static Load_Configuration commonConfig;

    public static void main(String[] args) {
        // TODO code application logic here
        SpringApplication.run(ApplicationProcess.class, args);
//        ConfigurableApplicationContext ConfAppContext = SpringApplication.run(ApplicationProcess.class, args);
//        ApplicationLaucher applicationLaucher = (ApplicationLaucher) ConfAppContext.getBean("ApplicationLaucher");

//        int send_thread_num = Integer.parseInt(commonConfig.getApplicationSenderNumberThread());
//        int send_thread_pool = Integer.parseInt(commonConfig.getApplicationSenderThreadPool());
//        int send_sleep_duration = Integer.parseInt(commonConfig.getApplicationSenderSleepDuration());
//        int send_maxQueue = Integer.parseInt(commonConfig.getApplicationSenderMaxQueue());
//
//        int process_reg_num = Integer.parseInt(commonConfig.getApplicationProcessRegNumberThread());
//        int process_reg_pool = Integer.parseInt(commonConfig.getApplicationProcessRegThreadPool());
//        int process_reg_sleep_duration = Integer.parseInt(commonConfig.getApplicationProcessRegSleepDuration());
//        int process_reg_maxQueue = Integer.parseInt(commonConfig.getApplicationProcessRegMaxQueue());
//
//        int process_check_num = Integer.parseInt(commonConfig.getApplicationProcessCheckNumberThread());
//        int process_check_pool = Integer.parseInt(commonConfig.getApplicationProcessCheckThreadPool());
//        int process_check_sleep_duration = Integer.parseInt(commonConfig.getApplicationProcessCheckSleepDuration());
//        int process_check_maxQueue = Integer.parseInt(commonConfig.getApplicationProcessCheckMaxQueue());
//
//        int process_del_num = Integer.parseInt(commonConfig.getApplicationProcessDelNumberThread());
//        int process_del_pool = Integer.parseInt(commonConfig.getApplicationProcessDelThreadPool());
//        int process_del_sleep_duration = Integer.parseInt(commonConfig.getApplicationProcessDelSleepDuration());
//        int process_del_maxQueue = Integer.parseInt(commonConfig.getApplicationProcessDelMaxQueue());
//
//        int process_guide_num = Integer.parseInt(commonConfig.getApplicationProcessGuideNumberThread());
//        int process_guide_pool = Integer.parseInt(commonConfig.getApplicationProcessGuideThreadPool());
//        int process_guide_sleep_duration = Integer.parseInt(commonConfig.getApplicationProcessGuideSleepDuration());
//        int process_guide_maxQueue = Integer.parseInt(commonConfig.getApplicationProcessGuideMaxQueue());
//
//        send_queue = new ArrayBlockingQueue<>(send_maxQueue);
//        reg_queue = new ArrayBlockingQueue<>(process_reg_maxQueue);
//        del_queue = new ArrayBlockingQueue<>(process_del_maxQueue);
//        check_queue = new ArrayBlockingQueue<>(process_check_maxQueue);
//        guide_queue = new ArrayBlockingQueue<>(process_guide_maxQueue);
//
//        List<Runnable> receiver_runnables = new ArrayList<Runnable>();
//        List<Runnable> sender_runnables = new ArrayList<Runnable>();
//        List<Runnable> process_reg_runnables = new ArrayList<Runnable>();
//        List<Runnable> process_del_runnables = new ArrayList<Runnable>();
//        List<Runnable> process_check_runnables = new ArrayList<Runnable>();
//        List<Runnable> process_guide_runnables = new ArrayList<Runnable>();
//
//        // sender
//        Sender.setSend_queue(send_queue);
//        for (int i = 0; i < send_thread_num; i++) {
//            Sender senderThread = (Sender) ConfAppContext.getBean("SenderThread");
//            senderThread.setSleep_duration(send_sleep_duration);
//            sender_runnables.add(senderThread);
//        }
//        ExecutorService send_Execute = Executors.newFixedThreadPool(send_thread_pool);
//        Sender.executeRunnables(send_Execute, sender_runnables);
//
//        // process reg
//        Process_Register.loadFeatures(reg_queue);
//        for (int i = 0; i < process_reg_num; i++) {
//            Process_Register regProcessThread = (Process_Register) ConfAppContext.getBean("RegisterProcessThread");
//            process_reg_runnables.add(regProcessThread);
//        }
//        ExecutorService process_Execute_reg = Executors.newFixedThreadPool(process_reg_pool);
//        Process_Register.executeRunnables(process_Execute_reg, process_reg_runnables);
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

}
