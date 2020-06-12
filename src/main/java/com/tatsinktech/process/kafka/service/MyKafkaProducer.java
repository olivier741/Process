package com.tatsinktech.process.kafka.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestParam;

@Configuration
public class MyKafkaProducer {

  Logger logger = LoggerFactory.getLogger(this.getClass().getName());

  @Value("${spring.kafka.producer.topic}")
  private String topic;

  @Autowired
  KafkaTemplate<String, String> kafkaTemplate;
  
  public void sendDataToKafka(String data) {

    ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(topic, data);

    listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

      @Override
      public void onSuccess(SendResult<String, String> result) {
        logger.info(String.format("Topic -->"+topic+" *** Message --> %s", result.getProducerRecord().value()));
      }

      @Override
      public void onFailure(Throwable ex) {
        logger.error("Unable to send data to Kafka", ex);
      }
    });
  }
  
  
   public void sendMessage(String message) {
       
            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
            
            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onSuccess(SendResult<String, String> result) {
                    System.out.println("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
                }

                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
                }
            });
        }
   
        public void sendMessageToPartion(String message, int partition) {
            kafkaTemplate.send(topic, partition, null, message);
        }




    

}
