package fr.polytech.ig5.mnm.offerms.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;

@Component
public class KafkaConsumer {

    @KafkaListener(topics="USER_DELETED", groupId = "1")
    void listener(String data){
        System.out.println(data);
    }

}
