package fr.polytech.ig5.mnm.recruitmentms.kafka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.polytech.ig5.mnm.recruitmentms.DTO.WorkCreateDTO;
import fr.polytech.ig5.mnm.recruitmentms.models.Work;
import fr.polytech.ig5.mnm.recruitmentms.services.WorkService;
import org.apache.tomcat.util.json.JSONParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class KafkaConsumer {
    WorkService workService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public KafkaConsumer(WorkService workService){
        this.workService = workService;
    }

    @KafkaListener(topics="WORKER_DELETED", groupId = "2")
    void workerDeletedListener(String workerId){
        this.workService.deleteByWorkerId(UUID.fromString(workerId));
    }

    @KafkaListener(topics="COMPANY_DELETED", groupId = "2")
    void companyDeletedListener(String companyId){
        this.workService.deleteByCompanyId(UUID.fromString(companyId));
    }

    @KafkaListener(topics="APPLICATION_ACCEPTED", groupId = "2")
    void applicationAcceptedListener(String work){
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        Map map = gson.fromJson(work, Map.class);

        WorkCreateDTO workDTO = new WorkCreateDTO(
                UUID.fromString(map.get("companyId").toString()),
                UUID.fromString(map.get("workerId").toString()),
                map.get("jobLabel").toString(),
                LocalDate.parse(map.get("startingDate").toString()),
                LocalDate.parse(map.get("endDate").toString()));

        this.workService.create(modelMapper.map(workDTO, Work.class));
    }

    @KafkaListener(topics="JOB_RATED_BY_WORKER", groupId = "2")
    void jobRatedByWorkerListener(String workId){
        Optional<Work> optionalWork = this.workService.find(UUID.fromString(workId));

        if(!optionalWork.isEmpty()){
            Work work = optionalWork.get();
            work.setIsRatedByEmployee(true);
            this.workService.update(work);
        }

    }

    @KafkaListener(topics="JOB_RATED_BY_COMPANY", groupId = "2")
    void jobRatedByCompanyListener(String workId){
        Optional<Work> optionalWork = this.workService.find(UUID.fromString(workId));

        if(!optionalWork.isEmpty()){
            Work work = optionalWork.get();
            work.setIsRatedByCompany(true);
            this.workService.update(work);
        }

    }

}
