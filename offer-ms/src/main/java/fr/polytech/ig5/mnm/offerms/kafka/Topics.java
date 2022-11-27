package fr.polytech.ig5.mnm.offerms.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class Topics {
    @Bean
    public NewTopic offerDeletedTopic(){
        return TopicBuilder.name("OFFER_DELETED").build();
    }

}
