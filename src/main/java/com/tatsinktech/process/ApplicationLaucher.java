/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.process;

import com.tatsinktech.process.beans.Process_Request;
import com.tatsinktech.process.config.Load_Configuration;
import com.tatsinktech.process.thread.register.Process_Check;
import com.tatsinktech.process.thread.register.Process_Delete;
import com.tatsinktech.process.thread.register.Process_Guide;
import com.tatsinktech.process.thread.register.Process_Register;
import com.tatsinktech.process.thread.sender.Sender;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author olivier.tatsinkou
 */
@Component
public class ApplicationLaucher implements InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(ApplicationLaucher.class);

    @Autowired
    private Load_Configuration commonConfig;

    @Autowired
    ConfigurableApplicationContext ConfAppContext;

    private static BlockingQueue<Process_Request> send_queue;
    private static BlockingQueue<Process_Request> reg_queue;
    private static BlockingQueue<Process_Request> del_queue;
    private static BlockingQueue<Process_Request> guide_queue;
    private static BlockingQueue<Process_Request> check_queue;

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("******************* InitializingBean *******************************");

        int send_thread_num = Integer.parseInt(commonConfig.getApplicationSenderNumberThread());
        int send_thread_pool = Integer.parseInt(commonConfig.getApplicationSenderThreadPool());
        int send_maxQueue = Integer.parseInt(commonConfig.getApplicationSenderMaxQueue());

        int process_reg_num = Integer.parseInt(commonConfig.getApplicationProcessRegNumberThread());
        int process_reg_pool = Integer.parseInt(commonConfig.getApplicationProcessRegThreadPool());
        int process_reg_maxQueue = Integer.parseInt(commonConfig.getApplicationProcessRegMaxQueue());

        int process_check_num = Integer.parseInt(commonConfig.getApplicationProcessCheckNumberThread());
        int process_check_pool = Integer.parseInt(commonConfig.getApplicationProcessCheckThreadPool());
        int process_check_maxQueue = Integer.parseInt(commonConfig.getApplicationProcessCheckMaxQueue());

        int process_del_num = Integer.parseInt(commonConfig.getApplicationProcessDelNumberThread());
        int process_del_pool = Integer.parseInt(commonConfig.getApplicationProcessDelThreadPool());
        int process_del_maxQueue = Integer.parseInt(commonConfig.getApplicationProcessDelMaxQueue());

        int process_guide_num = Integer.parseInt(commonConfig.getApplicationProcessGuideNumberThread());
        int process_guide_pool = Integer.parseInt(commonConfig.getApplicationProcessGuideThreadPool());
        int process_guide_maxQueue = Integer.parseInt(commonConfig.getApplicationProcessGuideMaxQueue());

        send_queue = new ArrayBlockingQueue<>(send_maxQueue);
        reg_queue = new ArrayBlockingQueue<>(process_reg_maxQueue);
        del_queue = new ArrayBlockingQueue<>(process_del_maxQueue);
        check_queue = new ArrayBlockingQueue<>(process_check_maxQueue);
        guide_queue = new ArrayBlockingQueue<>(process_guide_maxQueue);

        List<Runnable> sender_runnables = new ArrayList<Runnable>();
        List<Runnable> process_reg_runnables = new ArrayList<Runnable>();
        List<Runnable> process_del_runnables = new ArrayList<Runnable>();
        List<Runnable> process_check_runnables = new ArrayList<Runnable>();
        List<Runnable> process_guide_runnables = new ArrayList<Runnable>();

        // sender
        Sender.setSend_queue(send_queue);
        for (int i = 0; i < send_thread_num; i++) {
            Sender senderThread = (Sender) ConfAppContext.getBean(Sender.class);
            sender_runnables.add(senderThread);
        }
        ExecutorService send_Execute = Executors.newFixedThreadPool(send_thread_pool);
        Sender.executeRunnables(send_Execute, sender_runnables);

        // process reg
        Process_Register.setReg_queue(reg_queue);
        for (int i = 0;
                i < process_reg_num;
                i++) {
            Process_Register regProcessThread = (Process_Register) ConfAppContext.getBean(Process_Register.class);
            process_reg_runnables.add(regProcessThread);
        }
        ExecutorService process_Execute_reg = Executors.newFixedThreadPool(process_reg_pool);
        Process_Register.executeRunnables(process_Execute_reg, process_reg_runnables);

//        // process check
//        Process_Check.setCheck_queue(check_queue);
//        for (int i = 0; i < process_check_num; i++) {
//            Process_Check checkProcessThread = (Process_Check) ConfAppContext.getBean(Process_Check.class);
//            process_check_runnables.add(checkProcessThread);
//        }
//        ExecutorService process_Execute_check = Executors.newFixedThreadPool(process_check_pool);
//        Process_Check.executeRunnables(process_Execute_check, process_check_runnables);
//
//        // process Delete
//        Process_Delete.setDelete_queue(del_queue);
//        for (int i = 0; i < process_del_num; i++) {
//            Process_Delete delProcessThread = (Process_Delete) ConfAppContext.getBean(Process_Delete.class);
//            process_del_runnables.add(delProcessThread);
//        }
//        ExecutorService process_Execute_del = Executors.newFixedThreadPool(process_del_pool);
//        Process_Delete.executeRunnables(process_Execute_del, process_del_runnables);
//
        // process Guide
        Process_Guide.setGuide_queue(guide_queue);
        for (int i = 0; i < process_guide_num; i++) {
            Process_Guide guideProcessThread = (Process_Guide) ConfAppContext.getBean(Process_Guide.class);
            process_guide_runnables.add(guideProcessThread);
        }
        ExecutorService process_Execute_guide = Executors.newFixedThreadPool(process_guide_pool);
        Process_Guide.executeRunnables(process_Execute_guide, process_guide_runnables);
    }

}
