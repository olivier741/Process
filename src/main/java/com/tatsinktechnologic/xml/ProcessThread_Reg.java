/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author olivier.tatsinkou
 */
@Root(name="chat_process")
public class ProcessThread_Reg {
    
    @Element(name="numberThread",required = true) 
    private int numberThread;
   
    @Element(name="threadPool",required = true) 
    private int threadPool ;

    @Element(name="sleep_duration",required = true) 
    private int sleep_duration;
    
    @Element(name="maxQueue",required = true) 
    private int maxQueue;


    public ProcessThread_Reg(  
                            @Element(name="numberThread",required = true) int numberThread ,
                            @Element(name="threadPool",required = true)  int threadPool,
                            @Element(name="sleep_duration",required = true)  int sleep_duration,
                            @Element(name="maxQueue",required = true) int maxQueue) {
        this.numberThread = numberThread;
        this.threadPool = threadPool;
        this.sleep_duration = sleep_duration;
        this.maxQueue=maxQueue;
    }

    public int getNumberThread() {
        return numberThread;
    }

    public int getThreadPool() {
        return threadPool;
    }

    public int getSleep_duration() {
        return sleep_duration;
    }

    public int getMaxQueue() {
        return maxQueue;
    }
    
    

    public void setNumberThread(int numberThread) {
        this.numberThread = numberThread;
    }

    public void setThreadPool(int threadPool) {
        this.threadPool = threadPool;
    }

    public void setSleep_duration(int sleep_duration) {
        this.sleep_duration = sleep_duration;
    }

    public void setMaxQueue(int maxQueue) {
        this.maxQueue = maxQueue;
    }

    @Override
    public String toString() {
        return "ProcessThread_Reg{" + "numberThread=" + numberThread + ", threadPool=" + threadPool + ", sleep_duration=" + sleep_duration + ", maxQueue=" + maxQueue + '}';
    }
    

}
