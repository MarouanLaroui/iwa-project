package fr.polytech.ig5.mnm.recruitmentms.controllers;

import fr.polytech.ig5.mnm.recruitmentms.models.Work;
import fr.polytech.ig5.mnm.recruitmentms.services.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/works") // 1
public class WorkController {

    @Autowired
    WorkService service;

    public WorkController(WorkService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<Work> index() {
        return this.service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Work> get(@PathVariable("id") Long id) {
        return this.service.find(id);
    }

    @PostMapping("/create")
    public Work create(@RequestBody Work worker) {
        return this.service.create(worker);
    }

    @DeleteMapping(value = "/{id}")
    public Boolean deletePost(@PathVariable Long id) {
        return this.service.delete(id);
    }


}
