package fr.polytech.ig5.mnm.userms.controllers;

import fr.polytech.ig5.mnm.userms.DTO.WorkerCreateDTO;
import fr.polytech.ig5.mnm.userms.DTO.WorkerLoginDTO;
import fr.polytech.ig5.mnm.userms.DTO.WorkerUpdateDTO;
import fr.polytech.ig5.mnm.userms.models.Worker;
import fr.polytech.ig5.mnm.userms.services.WorkerService;
import fr.polytech.ig5.mnm.userms.utils.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/workers")
public class WorkerController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    WorkerService service;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    public WorkerController(WorkerService service) {
        super();
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
    public ResponseEntity<Object> get(@PathVariable("id") UUID id) {
        Optional<Worker> worker = service.find(id);

        if(worker.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Worker not found");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(worker);    }

    @PostMapping("/register")
    public ResponseEntity<Object> create(@Valid @RequestBody WorkerCreateDTO workerDTO) {
        workerDTO.setPassword(passwordEncoder.encode(workerDTO.getPassword()));
        Worker worker = modelMapper.map(workerDTO, Worker.class);
        Worker workerCreated = service.create(worker);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(workerCreated);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody WorkerLoginDTO workerLoginDTO) {

        String email = workerLoginDTO.getEmail();

        Optional<Worker> optionalWorker = this.service.findByEmail(email);
        if (optionalWorker.isEmpty()) return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("This account does not exist");

        Worker worker = optionalWorker.get();

        Map<String, Object> claims = new HashMap<>();
        claims.put("workerId", worker.getId());

        if (passwordEncoder.matches(workerLoginDTO.getPassword(), worker.getPassword())) {
            String token = jwtUtils.createJWT(claims, 1 * 60 * 60 * 1000);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(token);
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Wrong password");
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> update(@PathVariable("id") UUID id,@Valid @RequestBody WorkerUpdateDTO workerDTO) {
        Worker worker = modelMapper.map(workerDTO, Worker.class);
        worker.setId(id);

        Worker updatedWork =
                service.update(worker);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedWork);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable UUID id) {
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
