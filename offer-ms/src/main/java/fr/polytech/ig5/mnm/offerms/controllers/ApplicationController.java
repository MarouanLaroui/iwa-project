package fr.polytech.ig5.mnm.offerms.controllers;

import com.google.gson.*;
import fr.polytech.ig5.mnm.offerms.DTO.ApplicationCreateDTO;
import fr.polytech.ig5.mnm.offerms.DTO.ApplicationUpdateDTO;
import fr.polytech.ig5.mnm.offerms.kafka.KafkaProducer;
import fr.polytech.ig5.mnm.offerms.models.Application;
import fr.polytech.ig5.mnm.offerms.models.Offer;
import fr.polytech.ig5.mnm.offerms.services.ApplicationService;
import fr.polytech.ig5.mnm.offerms.services.OfferService;
import fr.polytech.ig5.mnm.offerms.utils.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("") // 1
public class ApplicationController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    ApplicationService applicationService;

    @Autowired
    OfferService offerService;

    private KafkaProducer producer;

    public ApplicationController(ApplicationService service, KafkaProducer producer) {
        this.applicationService = service;
        this.producer = producer;
    }

    @GetMapping("/applications/")
    public ResponseEntity<Object> findByWorkerId(
            @RequestHeader (name="Authorization") String bearerToken) {

        UUID workerId = jwtUtils.extractUUIDFromJWT("workerId", bearerToken);

        List <Application> applications = this.applicationService.findByWorkerId(workerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(applications);
    }

    @GetMapping("/applications/findByOfferId/{offerId}")
    public ResponseEntity<Object> findByOfferId(
            @PathVariable("offerId") UUID offerId,
            @RequestHeader (name="Authorization") String bearerToken) {

        UUID companyId = jwtUtils.extractUUIDFromJWT("companyId", bearerToken);
        Optional<Offer> optionalOffer = offerService.find(offerId);

        if(optionalOffer.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Offer not found");
        }

        Offer offer = optionalOffer.get();
        if(!offer.getCompanyId().equals(companyId)){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized");
        }

        List <Application> applications = this.applicationService.findByOffer(offer);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(applications);
    }

    @GetMapping("/applications/{id}")
    public ResponseEntity<Object> get(
            @PathVariable("id") UUID id,
            @RequestHeader (name="Authorization") String bearerToken) {

        Optional<Application> optionalApplication = this.applicationService.find(id);

        if(optionalApplication.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Application not found");
        }

        Application application = optionalApplication.get();

        UUID workerId = jwtUtils.extractUUIDFromJWT("workerId", bearerToken);
        UUID companyId = jwtUtils.extractUUIDFromJWT("companyId", bearerToken);

        if(companyId != null) {
            if(!application.getOffer().getCompanyId().equals(companyId)){
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Unauthorized");
            }
        } else {
            if(!application.getWorkerId().equals(workerId)){
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Unauthorized");
            }
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(application);
    }

    @PostMapping("/offers/{offerId}/applications")
    public ResponseEntity<Object> create(
            @PathVariable("offerId") UUID offerId,
            @Valid @RequestBody ApplicationCreateDTO applicationDTO,
            @RequestHeader (name="Authorization") String bearerToken) {

        UUID workerId = jwtUtils.extractUUIDFromJWT("workerId", bearerToken);

        Optional<Offer> optionalOffer = offerService.find(offerId);

        if(optionalOffer.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Offer not found");
        }
        Offer offer = optionalOffer.get();

        Optional<Application> optionalApplication = this.applicationService.findApplicationByWorkerIdAndOffer(workerId, offer);

        if(!optionalApplication.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Already applied for this offer");
        }

        Application applicationToCreate = modelMapper.map(applicationDTO, Application.class);
        applicationToCreate.setOffer(offer);
        applicationToCreate.setWorkerId(workerId);

        Application applicationCreated = this.applicationService.create(applicationToCreate);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(applicationCreated);
    }

    @PutMapping(value = "/applications/acceptByWorker/{id}")
    public ResponseEntity<Object> acceptByWorker(
            @PathVariable("id") UUID id,
            @RequestHeader (name="Authorization") String bearerToken) {

        UUID workerId = jwtUtils.extractUUIDFromJWT("workerId", bearerToken);

        Optional<Application> optionalApplication = this.applicationService.find(id);
        if(optionalApplication.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Application not found");
        }
        Application application = optionalApplication.get();

        if(!application.getWorkerId().equals(workerId)){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized");
        }

        if(!application.getIsValidatedByCompany()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Application not yet validated by the company");
        }

        // tout est bon
        application.setIsValidatedByWorker(true);
        Application updatedApplication =
                applicationService.update(application);

        ledToJob(application);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedApplication);

    }

    @PutMapping(value = "/applications/acceptByCompany/{id}")
    public ResponseEntity<Object> acceptByCompany(
            @PathVariable("id") UUID id,
            @RequestHeader (name="Authorization") String bearerToken) {

        UUID companyId = jwtUtils.extractUUIDFromJWT("companyId", bearerToken);

        Optional<Application> optionalApplication = this.applicationService.find(id);
        if(optionalApplication.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Application not found");
        }
        Application application = optionalApplication.get();

        UUID companyIdFromOffer = application.getOffer().getCompanyId();
        if(!companyIdFromOffer.equals(companyId)){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized");
        }

        // tout est bon
        application.setIsValidatedByCompany(true);
        Application updatedApplication =
                applicationService.update(application);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedApplication);

    }

    /* Plus d'utilisation pour le moment

    @PutMapping(value = "applications/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") UUID id, @Valid @RequestBody ApplicationUpdateDTO applicationDTO) {

        Optional<Application> application = this.applicationService.find(id);
        if(application.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Offer not found");
        }
        Application app = application.get();

        app.setIsValidatedByCompany(applicationDTO.getIsValidatedByCompany());
        app.setIsValidatedByWorker(applicationDTO.getIsValidatedByWorker());

        Application updatedApplication =
                applicationService.update(app);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedApplication);
    }
     */

    @DeleteMapping(value = "/applications/{id}")
    public ResponseEntity<Object> deleteOffer(
            @PathVariable UUID id,
            @RequestHeader (name="Authorization") String bearerToken) {

        UUID workerId = jwtUtils.extractUUIDFromJWT("workerId", bearerToken);

        Optional<Application> optionalApplication = this.applicationService.find(id);
        if(optionalApplication.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Application not found");
        }
        Application application = optionalApplication.get();

        if(!application.getWorkerId().equals(workerId)){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized");
        }

        var isRemoved = this.applicationService.delete(id);

        if (!isRemoved) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Application not found");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(id);
    }

    private void ledToJob(Application application){
        // create work
        HashMap<String, String> work = new HashMap<String, String>();
        work.put("companyId", application.getOffer().getCompanyId().toString());
        work.put("workerId", application.getWorkerId().toString());
        work.put("jobLabel", application.getOffer().getTitle());
        work.put("startingDate", application.getOffer().getStartingDate().toString());
        work.put("endDate", application.getOffer().getEndDate().toString());

        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        producer.sendMessage("APPLICATION_ACCEPTED", gson.toJson(work));

        // update offer
        Offer offer = application.getOffer();
        offer.setLedToJob(true);
        this.offerService.update(offer);
    }

}
