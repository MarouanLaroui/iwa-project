package fr.polytech.ig5.mnm.offerms.kafka;

import fr.polytech.ig5.mnm.offerms.services.ApplicationService;
import fr.polytech.ig5.mnm.offerms.services.CriteriaService;
import fr.polytech.ig5.mnm.offerms.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class KafkaConsumer {
    OfferService offerService;
    ApplicationService applicationService;
    CriteriaService criteriaService;

    @Autowired
    public KafkaConsumer(
            OfferService offerService,
            ApplicationService applicationService,
            CriteriaService criteriaService
    ){
        this.offerService = offerService;
        this.applicationService = applicationService;
        this.criteriaService = criteriaService;
    }

    @KafkaListener(topics="WORKER_DELETED", groupId = "1")
    void workerDeletedListener(String workerId){
        this.applicationService.deleteByWorkerId(UUID.fromString(workerId));
        this.criteriaService.deleteByWorkerId(UUID.fromString(workerId));
    }

    @KafkaListener(topics="COMPANY_DELETED", groupId = "1")
    void companyDeletedListner(String companyId){
        this.offerService.deleteByCompanyId(UUID.fromString(companyId));
    }

}
