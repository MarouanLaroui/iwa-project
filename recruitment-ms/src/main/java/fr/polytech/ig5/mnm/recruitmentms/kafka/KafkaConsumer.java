package fr.polytech.ig5.mnm.recruitmentms.kafka;

import fr.polytech.ig5.mnm.recruitmentms.services.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class KafkaConsumer {
    WorkService workService;

    @Autowired
    public KafkaConsumer(WorkService workService){
        this.workService = workService;
    }

    @KafkaListener(topics="WORKER_DELETED", groupId = "1")
    void workerDeletedListener(String workerId){
        this.workService.deleteByWorkerId(UUID.fromString(workerId));
    }

    @KafkaListener(topics="COMPANY_DELETED", groupId = "1")
    void companyDeletedListener(String companyId){
        this.workService.deleteByCompanyId(UUID.fromString(companyId));
    }

}
