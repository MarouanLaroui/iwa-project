package fr.polytech.ig5.mnm.userms.controllers;

import fr.polytech.ig5.mnm.userms.DTO.WorkerCreateDTO;
import fr.polytech.ig5.mnm.userms.DTO.LoginDTO;
import fr.polytech.ig5.mnm.userms.DTO.WorkerGetDTO;
import fr.polytech.ig5.mnm.userms.DTO.WorkerUpdateDTO;
import fr.polytech.ig5.mnm.userms.models.Worker;
import fr.polytech.ig5.mnm.userms.services.WorkerService;
import fr.polytech.ig5.mnm.userms.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

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

        List<WorkerGetDTO> workersPurified = workers.stream()
                .map(worker -> modelMapper.map(worker, WorkerGetDTO.class))
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(workersPurified);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable("id") UUID id) {
        Optional<Worker> worker = service.find(id);

        if(worker.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Worker not found");
        }

        WorkerGetDTO workerPurified =
                modelMapper.map(worker.get(), WorkerGetDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(workerPurified);    }

    @PostMapping("/register")
    public ResponseEntity<Object> create(@Valid @RequestBody WorkerCreateDTO workerDTO) {
        workerDTO.setPassword(passwordEncoder.encode(workerDTO.getPassword()));
        Worker worker = modelMapper.map(workerDTO, Worker.class);

        WorkerGetDTO workerCreated =
                modelMapper.map(service.create(worker), WorkerGetDTO.class);

        Map<String, Object> claims = new HashMap<>();
        claims.put("workerId", worker.getId());
        String token = jwtUtils.createJWT(claims, 1 * 60 * 60 * 1000);

        workerCreated.setAuthorizationToken(token);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(workerCreated);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDTO loginDTO) {

        String email = loginDTO.getEmail();

        Optional<Worker> optionalWorker = this.service.findByEmail(email);
        if (optionalWorker.isEmpty()) return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("This account does not exist");

        Worker worker = optionalWorker.get();

        Map<String, Object> claims = new HashMap<>();
        claims.put("workerId", worker.getId());

        if (passwordEncoder.matches(loginDTO.getPassword(), worker.getPassword())) {
            String token = jwtUtils.createJWT(claims, 1 * 60 * 60 * 1000);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(token);
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Wrong password");
    }

    @PutMapping(value = "/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> update(
            @Valid @RequestBody WorkerUpdateDTO workerDTO,
            @RequestHeader (name="Authorization") String bearerToken
    ) {

        UUID workerId = jwtUtils.extractUUIDFromJWT("workerId", bearerToken);
        Optional<Worker> optionalWorkerToUpdate = this.service.find(workerId);
        if (optionalWorkerToUpdate.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Worker not found");
        }
        Worker worker = optionalWorkerToUpdate.get();
        worker.setFirstname(workerDTO.getFirstname() == null ? worker.getFirstname() : workerDTO.getFirstname());
        worker.setLastname(workerDTO.getLastname() == null ? worker.getLastname() : workerDTO.getLastname());
        worker.setEmail(workerDTO.getEmail() == null ? worker.getEmail() : workerDTO.getEmail());
        worker.setPassword(workerDTO.getPassword() == null ? worker.getPassword() : passwordEncoder.encode(workerDTO.getPassword()));
        worker.setCvLink(workerDTO.getCvLink() == null ? worker.getCvLink() : workerDTO.getCvLink());
        worker.setBirthDate(workerDTO.getBirthDate() == null ? worker.getBirthDate() : workerDTO.getBirthDate());
        worker.setHasDrivingLicense(workerDTO.getHasDrivingLicense() == null ? worker.getHasDrivingLicense() : workerDTO.getHasDrivingLicense());

        WorkerGetDTO updatedWorker =
                modelMapper.map(service.update(worker), WorkerGetDTO.class);


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedWorker);
    }

    @DeleteMapping(value = "/")
    public ResponseEntity<Object> deletePost(@RequestHeader (name="Authorization") String bearerToken) {
        UUID workerId = jwtUtils.extractUUIDFromJWT("workerId", bearerToken);
        Boolean isRemoved = this.service.delete(workerId);

        if(!isRemoved){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Worker not found");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(workerId);    }


}
