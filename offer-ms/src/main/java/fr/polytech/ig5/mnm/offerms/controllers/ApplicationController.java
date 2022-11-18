package fr.polytech.ig5.mnm.offerms.controllers;

import fr.polytech.ig5.mnm.offerms.DTO.ApplicationCreateDTO;
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
    public List<Application> index() {
        return this.applicationService.findAll();
    }

    @GetMapping("/applications/{id}")
    public Optional<Application> get(@PathVariable("id") Long id) {
        return this.applicationService.find(id);
    }

    @PostMapping("offers/{offerId}/applications")
    public Application create(@PathVariable("offerId") Long offerId, @Valid @RequestBody ApplicationCreateDTO applicationDTO) {
        Optional<Offer> offer = offerService.find(offerId);

        if(offer.isEmpty()){
            // do something when ResponseEntity will be setup
        }

        Application application = modelMapper.map(applicationDTO, Application.class);
        application.setOffer(offer.get());

        return this.applicationService.create(application);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteOffer(@PathVariable Long id) {

        var isRemoved = this.applicationService.delete(id);

        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // doesn't work, empty return in postman
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
