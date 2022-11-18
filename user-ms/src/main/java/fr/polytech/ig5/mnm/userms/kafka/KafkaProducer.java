package fr.polytech.ig5.mnm.userms.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    private KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    KafkaProducer(KafkaTemplate<String,String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message, String topicName){
        kafkaTemplate.send(topicName, message);
    }
}
