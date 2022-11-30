package fr.polytech.ig5.mnm.feedbackms.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class Topics {
    @Bean
    public NewTopic jobRatedByWorkerTopic(){
        return TopicBuilder.name("JOB_RATED_BY_WORKER").build();
    }

    @Bean
    public NewTopic jobRatedByCompanyTopic(){
        return TopicBuilder.name("JOB_RATED_BY_COMPANY").build();
    }
}
