package fr.polytech.ig5.mnm.offerms.controllers;

import fr.polytech.ig5.mnm.offerms.DTO.CriteriaCreateDTO;
import fr.polytech.ig5.mnm.offerms.DTO.CriteriaUpdateDTO;
import fr.polytech.ig5.mnm.offerms.models.Criteria;
import fr.polytech.ig5.mnm.offerms.services.CriteriaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/criterias") // 1
public class CriteriaController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    CriteriaService service;

    public CriteriaController(CriteriaService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<Object> index() {
        List<Criteria> criterias = this.service.findAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(criterias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable("id") Long id) {
        Optional<Criteria> criteria = this.service.find(id);

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
    public ResponseEntity<Object> create(@Valid @RequestBody CriteriaCreateDTO criteriaDTO) {
        Criteria criteria = this.service.create(modelMapper.map(criteriaDTO, Criteria.class));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(criteria);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @Valid @RequestBody CriteriaUpdateDTO criteriaDTO) {
        // on s'assure qu'il à bien le bon id
        criteriaDTO.setCriteriaId(id);

        Criteria updatedCriteria =
                service.update(modelMapper.map(criteriaDTO, Criteria.class));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedCriteria);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteOffer(@PathVariable Long id) {

        var isRemoved = this.service.delete(id);

        if (!isRemoved) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Criteria not found");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(id);
    }


}
