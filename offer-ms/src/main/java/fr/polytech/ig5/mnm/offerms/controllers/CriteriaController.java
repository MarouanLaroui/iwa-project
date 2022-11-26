package fr.polytech.ig5.mnm.offerms.controllers;

import fr.polytech.ig5.mnm.offerms.DTO.CriteriaCreateDTO;
import fr.polytech.ig5.mnm.offerms.DTO.CriteriaUpdateDTO;
import fr.polytech.ig5.mnm.offerms.models.Criteria;
import fr.polytech.ig5.mnm.offerms.services.CriteriaService;
import fr.polytech.ig5.mnm.offerms.utils.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/criterias") // 1
public class CriteriaController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    CriteriaService service;

    public CriteriaController(CriteriaService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<Object> get(
            @RequestHeader (name="Authorization") String bearerToken) {

        UUID workerId = jwtUtils.extractUUIDFromJWT("workerId", bearerToken);

        Optional<Criteria> criteria = this.service.findCriteriaByWorkerId(workerId);

        if(criteria.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Criteria not found");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(criteria);
    }

    @PostMapping("/")
    public ResponseEntity<Object> create(
            @Valid @RequestBody CriteriaCreateDTO criteriaDTO,
            @RequestHeader (name="Authorization") String bearerToken) {

        UUID workerId = jwtUtils.extractUUIDFromJWT("workerId", bearerToken);
        Criteria newCriteria = modelMapper.map(criteriaDTO, Criteria.class);
        newCriteria.setWorkerId(workerId);

        Optional<Criteria> optionalCriteria = this.service.findCriteriaByWorkerId(workerId);

        if(optionalCriteria.isEmpty()) {
            Criteria createdCriteria = this.service.create(newCriteria);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(createdCriteria);
        } else {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("A worker can't have multiple criteria");
        }
    }

    @PutMapping("/")
    public ResponseEntity<Object> update(
            @Valid @RequestBody CriteriaCreateDTO criteriaDTO,
            @RequestHeader (name="Authorization") String bearerToken) {

        UUID workerId = jwtUtils.extractUUIDFromJWT("workerId", bearerToken);
        Criteria newCriteria = modelMapper.map(criteriaDTO, Criteria.class);
        newCriteria.setWorkerId(workerId);

        Optional<Criteria> optionalCriteria = this.service.findCriteriaByWorkerId(workerId);

        if(optionalCriteria.isEmpty()) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Criteria not found");

        }
        Criteria criteria = optionalCriteria.get();

        newCriteria.setCriteriaId(criteria.getCriteriaId());
        Criteria createdCriteria = this.service.update(newCriteria);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createdCriteria);
    }

    @DeleteMapping(value = "/")
    public ResponseEntity<Object> deleteOffer(
            @RequestHeader (name="Authorization") String bearerToken) {

        UUID workerId = jwtUtils.extractUUIDFromJWT("workerId", bearerToken);

        var isRemoved = this.service.deleteByWorkerId(workerId);

        if (!isRemoved) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Criteria not found");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(true);
    }


}
