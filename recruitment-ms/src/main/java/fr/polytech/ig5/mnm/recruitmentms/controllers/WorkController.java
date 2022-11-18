package fr.polytech.ig5.mnm.recruitmentms.controllers;

import fr.polytech.ig5.mnm.recruitmentms.DTO.WorkDTO;
import fr.polytech.ig5.mnm.recruitmentms.models.Work;
import fr.polytech.ig5.mnm.recruitmentms.services.WorkService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/works") // 1
public class WorkController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    WorkService service;

    public WorkController(WorkService service) {
        super();
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<Object> index() {
        List<Work> works = this.service.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(works);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable(name = "id") Long id) {
        Optional<Work> work = service.find(id);

        if(work.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Work not found");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(work);
    }

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody WorkDTO workDTO) {
        Work work = modelMapper.map(workDTO, Work.class);
        Work workCreated = service.create(work);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(workCreated);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
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
