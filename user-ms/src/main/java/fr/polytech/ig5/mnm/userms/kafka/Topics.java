package fr.polytech.ig5.mnm.userms.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class Topics {
    @Bean
    public NewTopic workerDeletedTopic(){
        return TopicBuilder.name("WORKER_DELETED").build();
    }

    @Bean
    public NewTopic companyDeletedTopic(){
        return TopicBuilder.name("COMPANY_DELETED").build();
    }
}
