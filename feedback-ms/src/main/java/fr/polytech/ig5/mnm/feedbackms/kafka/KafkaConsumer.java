package fr.polytech.ig5.mnm.feedbackms.kafka;

import fr.polytech.ig5.mnm.feedbackms.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class KafkaConsumer {
    FeedbackService feedbackService;

    @Autowired
    public KafkaConsumer(FeedbackService feedbackService){
        this.feedbackService = feedbackService;
    }

    @KafkaListener(topics="WORKER_DELETED", groupId = "3")
    void workerDeletedListener(String workerId){
        this.feedbackService.deleteByReceiverId(UUID.fromString(workerId));
    }

    @KafkaListener(topics="COMPANY_DELETED", groupId = "3")
    void companyDeletedListener(String companyId){
        this.feedbackService.deleteByReceiverId(UUID.fromString(companyId));
    }

}
