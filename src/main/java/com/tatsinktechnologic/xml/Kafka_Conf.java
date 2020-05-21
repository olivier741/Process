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
@Root(name="kafka")
public class Kafka_Conf {

    @Element(name="numberRecord",required = true) 
    private int numberRecord;
    
    @Element(name="consumer_topic",required = true)
    private String consumer_topic;
    
    @Element(name="producer_topic",required = true)
    private String producer_topic;
    
     public Kafka_Conf( @Element(name="numberRecord",required = true)  int numberRecord,
                        @Element(name="consumer_topic",required = true) String consumer_topic,
                        @Element(name="producer_topic",required = true) String producer_topic) {
        this.numberRecord = numberRecord;
        this.consumer_topic = consumer_topic;
        this.producer_topic = producer_topic;
    }

    public int getNumberRecord() {
        return numberRecord;
    }

    public String getConsumer_topic() {
        return consumer_topic;
    }

    public String getProducer_topic() {
        return producer_topic;
    }

    public void setNumberRecord(int numberRecord) {
        this.numberRecord = numberRecord;
    }

    public void setConsumer_topic(String consumer_topic) {
        this.consumer_topic = consumer_topic;
    }

    public void setProducer_topic(String producer_topic) {
        this.producer_topic = producer_topic;
    }

    @Override
    public String toString() {
        return "Kafka_Conf{" + "numberRecord=" + numberRecord + ", consumer_topic=" + consumer_topic + ", producer_topic=" + producer_topic + '}';
    }
     
     
}
