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
    public ResponseEntity<Object> index() {
        List<Worker> workers = this.service.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(workers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable("id") Long id) {
        Optional<Worker> worker = service.find(id);

        if(worker.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Worker not found");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(worker);    }

    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody Worker worker) {
        // Worker worker = modelMapper.map(workDTO, Work.class);
        Worker workerCreated = service.create(worker);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(workerCreated);    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable Long id) {
        Boolean isRemoved = this.service.delete(id);

        if(!isRemoved){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Worker not found");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(id);    }


}
