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
@Root(name="receiver_thread")
public class ReceiverThread_Conf {
    @Element(name="numberThread",required = true) 
    private int numberThread;
   
    @Element(name="threadPool",required = true) 
    private int threadPool ;

    @Element(name="sleep_duration",required = true) 
    private int sleep_duration;
    


    public ReceiverThread_Conf(  
                            @Element(name="numberThread",required = true) int numberThread ,
                            @Element(name="threadPool",required = true)  int threadPool,
                            @Element(name="sleep_duration",required = true)  int sleep_duration) {
        this.numberThread = numberThread;
        this.threadPool = threadPool;
        this.sleep_duration = sleep_duration;
        
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

    public void setNumberThread(int numberThread) {
        this.numberThread = numberThread;
    }

    public void setThreadPool(int threadPool) {
        this.threadPool = threadPool;
    }

    public void setSleep_duration(int sleep_duration) {
        this.sleep_duration = sleep_duration;
    }

    @Override
    public String toString() {
        return "ReceiverThread_Conf{" + "numberThread=" + numberThread + ", threadPool=" + threadPool + ", sleep_duration=" + sleep_duration + '}';
    }

    
}
