package fr.polytech.ig5.mnm.userms.controllers;

import fr.polytech.ig5.mnm.userms.models.Worker;
import fr.polytech.ig5.mnm.userms.services.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/workers")
public class WorkerController {

    @Autowired
    WorkerService service;

    public WorkerController(WorkerService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<Worker> index() {
        return this.service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Worker> get(@PathVariable("id") Long id) {
        return this.service.find(id);
    }

    @PostMapping("/create")
    public Worker create(@RequestBody Worker worker) {
        return this.service.create(worker);
    }

    @DeleteMapping(value = "/{id}")
    public Boolean deletePost(@PathVariable Long id) {
        return this.service.delete(id);
    }


}
