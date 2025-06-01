package com.example.bank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    public void pushMessage(String topic,Object message) {
        kafkaTemplate.send(topic,message);
    }
}
