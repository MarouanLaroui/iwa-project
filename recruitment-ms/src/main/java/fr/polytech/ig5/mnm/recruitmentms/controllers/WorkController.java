package fr.polytech.ig5.mnm.recruitmentms.controllers;

import fr.polytech.ig5.mnm.recruitmentms.DTO.WorkUpdateDTO;
import fr.polytech.ig5.mnm.recruitmentms.models.Work;
import fr.polytech.ig5.mnm.recruitmentms.services.WorkService;
import fr.polytech.ig5.mnm.recruitmentms.utils.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/works") // 1
public class WorkController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    WorkService service;

    public WorkController(WorkService service) {
        super();
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<Object> findByCompanyId(
            @RequestHeader (name="Authorization") String bearerToken) {

        UUID workerId = jwtUtils.extractUUIDFromJWT("workerId", bearerToken);
        UUID companyId = jwtUtils.extractUUIDFromJWT("companyId", bearerToken);

        List<Work> works;
        if(companyId != null) {
            works = this.service.findByCompanyId(companyId);
        } else if (workerId != null){
            works = this.service.findByWorkerId(workerId);
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(works);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(
            @PathVariable(name = "id") UUID id,
            @RequestHeader (name="Authorization") String bearerToken) {

        UUID workerId = jwtUtils.extractUUIDFromJWT("workerId", bearerToken);
        UUID companyId = jwtUtils.extractUUIDFromJWT("companyId", bearerToken);

        Optional<Work> optionalWork = service.find(id);

        if (optionalWork.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Work not found");
        }

        Work work = optionalWork.get();

        if(companyId != null) {
            if(!work.getCompanyId().equals(companyId)){
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Unauthorized");
            }
        } else if (workerId != null){
            if(!work.getWorkerId().equals(workerId)){
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Unauthorized");
            }
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(work);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> update(
            @PathVariable("id") UUID id,
            @RequestBody WorkUpdateDTO workDTO,
            @RequestHeader (name="Authorization") String bearerToken) {

        UUID workerId = jwtUtils.extractUUIDFromJWT("workerId", bearerToken);
        UUID companyId = jwtUtils.extractUUIDFromJWT("companyId", bearerToken);

        Optional<Work> optionalWork = service.find(id);

        if (optionalWork.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Work not found");
        }

        Work work = optionalWork.get();

        if(companyId != null) {
            if(!work.getCompanyId().equals(companyId)){
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Unauthorized");
            }
        } else if (workerId != null){
            if(!work.getWorkerId().equals(workerId)){
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Unauthorized");
            }
        }

        Work newWork = modelMapper.map(workDTO, Work.class);
        // immutable field
        newWork.setWorkId(work.getWorkId());
        newWork.setWorkerId(work.getWorkerId());
        newWork.setCompanyId(work.getCompanyId());

        Work updatedWork = service.update(newWork);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedWork);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> delete(
            @PathVariable UUID id,
            @RequestHeader (name="Authorization") String bearerToken) {

        UUID workerId = jwtUtils.extractUUIDFromJWT("workerId", bearerToken);
        UUID companyId = jwtUtils.extractUUIDFromJWT("companyId", bearerToken);

        Optional<Work> optionalWork = service.find(id);

        if (optionalWork.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Work not found");
        }

        Work work = optionalWork.get();

        if(companyId != null) {
            if(!work.getCompanyId().equals(companyId)){
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Unauthorized");
            }
        } else if (workerId != null){
            if(!work.getWorkerId().equals(workerId)){
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Unauthorized");
            }
        }
        Boolean isRemoved = this.service.delete(id);

        if(!isRemoved){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Work not found");
        }
        // on retourne l'id de l'object que l'on vient de supprimer
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(id);
    }

}
