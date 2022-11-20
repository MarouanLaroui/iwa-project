package fr.polytech.ig5.mnm.offerms.controllers;

import fr.polytech.ig5.mnm.offerms.DTO.ApplicationCreateDTO;
import fr.polytech.ig5.mnm.offerms.DTO.ApplicationUpdateDTO;
import fr.polytech.ig5.mnm.offerms.models.Application;
import fr.polytech.ig5.mnm.offerms.models.Offer;
import fr.polytech.ig5.mnm.offerms.services.ApplicationService;
import fr.polytech.ig5.mnm.offerms.services.OfferService;
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
@RequestMapping("") // 1
public class ApplicationController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ApplicationService applicationService;

    @Autowired
    OfferService offerService;

    public ApplicationController(ApplicationService service) {
        this.applicationService = service;
    }

    @GetMapping("/applications/")
    public ResponseEntity<Object> index() {
        List <Application> applications = this.applicationService.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(applications);
    }

    @GetMapping("/applications/{id}")
    public ResponseEntity<Object> get(@PathVariable("id") UUID id) {
        Optional<Application> application = this.applicationService.find(id);

        if(application.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Application not found");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(application);
    }

    @PostMapping("offers/{offerId}/applications")
    public ResponseEntity<Object> create(@PathVariable("offerId") UUID offerId, @Valid @RequestBody ApplicationCreateDTO applicationDTO) {
        Optional<Offer> offer = offerService.find(offerId);

        if(offer.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Offer not found");
        }

        Application application = modelMapper.map(applicationDTO, Application.class);
        application.setOffer(offer.get());

        Application applicationCreated = this.applicationService.create(application);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(applicationCreated);
    }

    @PutMapping(value = "applications/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") UUID id, @Valid @RequestBody ApplicationUpdateDTO applicationDTO) {
        // on s'assure qu'il à bien le bon id
        applicationDTO.setApplicationId(id);

        Optional<Application> application = this.applicationService.find(id);
        if(application.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Offer not found");
        }
        Application app = application.get();

        app.setIsValidatedByCompany(applicationDTO.getIsValidatedByCompany());
        app.setIsValidatedByWorker(applicationDTO.getIsValidatedByWorker());

        // copy immutable field
        //application.setWorkerId(app.get().getWorkerId());
        //application.setOffer(app.get().getOffer());

        Application updatedApplication =
                applicationService.update(app);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedApplication);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteOffer(@PathVariable UUID id) {

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


}
