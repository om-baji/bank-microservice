package com.example.bank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public void pushMessage(String topic,String message) {
        kafkaTemplate.send(topic,message);
    }
}
