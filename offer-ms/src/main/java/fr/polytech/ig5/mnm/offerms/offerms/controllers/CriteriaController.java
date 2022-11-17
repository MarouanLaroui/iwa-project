package fr.polytech.ig5.mnm.offerms.offerms.controllers;

import fr.polytech.ig5.mnm.offerms.offerms.models.Criteria;
import fr.polytech.ig5.mnm.offerms.offerms.services.CriteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/criterias") // 1
public class CriteriaController {

    @Autowired
    CriteriaService service;

    public CriteriaController(CriteriaService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<Criteria> index() {
        return this.service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Criteria> get(@PathVariable("id") Long id) {
        return this.service.find(id);
    }

    @PostMapping("/create")
    public Criteria create(@RequestBody Criteria application) {
        return this.service.create(application);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteOffer(@PathVariable Long id) {

        var isRemoved = this.service.delete(id);

        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // doesn't work, empty return in postman
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
