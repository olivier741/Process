/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.xml;

import java.util.ArrayList;
import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 *
 * @author olivier.tatsinkou
 */
@Root(name="application")
public class Application {
    
    @Element(name="kafka",required = true) 
    private Kafka_Conf kafka_conf;

    @Element(name="sender_thread",required = true) 
    private SenderThread_Conf sender_thread;
    
    @Element(name="receiver_thread",required = true) 
    private ReceiverThread_Conf receiver_thread;
    
    @Element(name="process_thread_reg",required = true) 
    private ProcessThread_Reg process_thread_reg;
    
    @Element(name="process_thread_del",required = true) 
    private ProcessThread_Del process_thread_del;
    
    @Element(name="process_thread_check",required = true) 
    private ProcessThread_Check process_thread_check; 
        
    @Element(name="process_thread_guide",required = true) 
    private ProcessThread_Guide process_thread_guide;
    

  
    public Application(@Element(name="kafka",required = true) Kafka_Conf kafka_conf,
                        @Element(name="sender_thread",required = true)  SenderThread_Conf sender_thread, 
                        @Element(name="receiver_thread",required = true) ReceiverThread_Conf receiver_thread,
                        @Element(name="process_thread_reg",required = true) ProcessThread_Reg process_thread_reg,
                        @Element(name="process_thread_del",required = true) ProcessThread_Del process_thread_del,
                        @Element(name="process_thread_check",required = true) ProcessThread_Check process_thread_check, 
                        @Element(name="process_thread_guide",required = true) ProcessThread_Guide process_thread_guide) {
        
        this.sender_thread = sender_thread;  
        this.receiver_thread=receiver_thread;
        this.process_thread_reg=process_thread_reg;
        this.process_thread_del=process_thread_del;
        this.process_thread_check=process_thread_check;
        this.process_thread_guide=process_thread_guide;
        this.kafka_conf=kafka_conf;
    }

    public Kafka_Conf getKafka_conf() {
        return kafka_conf;
    }

    public SenderThread_Conf getSender_thread() {
        return sender_thread;
    }

    public ReceiverThread_Conf getReceiver_thread() {
        return receiver_thread;
    }

    public ProcessThread_Reg getProcess_thread_reg() {
        return process_thread_reg;
    }

    public ProcessThread_Del getProcess_thread_del() {
        return process_thread_del;
    }

    public ProcessThread_Check getProcess_thread_check() {
        return process_thread_check;
    }

    public ProcessThread_Guide getProcess_thread_guide() {
        return process_thread_guide;
    }

    public void setKafka_conf(Kafka_Conf kafka_conf) {
        this.kafka_conf = kafka_conf;
    }

    public void setSender_thread(SenderThread_Conf sender_thread) {
        this.sender_thread = sender_thread;
    }

    public void setReceiver_thread(ReceiverThread_Conf receiver_thread) {
        this.receiver_thread = receiver_thread;
    }

    public void setProcess_thread_reg(ProcessThread_Reg process_thread_reg) {
        this.process_thread_reg = process_thread_reg;
    }

    public void setProcess_thread_del(ProcessThread_Del process_thread_del) {
        this.process_thread_del = process_thread_del;
    }

    public void setProcess_thread_check(ProcessThread_Check process_thread_check) {
        this.process_thread_check = process_thread_check;
    }

    public void setProcess_thread_guide(ProcessThread_Guide process_thread_guide) {
        this.process_thread_guide = process_thread_guide;
    }

    @Override
    public String toString() {
        return "Application{" + "kafka_conf=" + kafka_conf + ", sender_thread=" + sender_thread + ", receiver_thread=" + receiver_thread + ", process_thread_reg=" + process_thread_reg + ", process_thread_del=" + process_thread_del + ", process_thread_check=" + process_thread_check + ", process_thread_guide=" + process_thread_guide + '}';
    }

  

    
  
 
}
