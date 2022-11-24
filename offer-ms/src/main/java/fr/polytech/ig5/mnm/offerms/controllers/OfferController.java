package fr.polytech.ig5.mnm.offerms.controllers;

import fr.polytech.ig5.mnm.offerms.DTO.OfferCreateDTO;
import fr.polytech.ig5.mnm.offerms.DTO.OfferUpdateDTO;
import fr.polytech.ig5.mnm.offerms.models.Offer;
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
@RequestMapping("/offers") // 1
public class OfferController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    OfferService service;

    public OfferController(OfferService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<Object> index() {
        List<Offer> offers = this.service.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(offers);
    }

    @GetMapping("/findByCompanyId/{companyId}")
    public ResponseEntity<Object> findByCompanyId(@PathVariable("companyId") UUID companyId) {
        List<Offer> offers = this.service.findByCompanyId(companyId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(offers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable("id") UUID id) {
        Optional<Offer> offer = this.service.find(id);

        if(offer.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Offer not found");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(offer);
    }

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody OfferCreateDTO offerDTO) {
        Offer offer = this.service.create(modelMapper.map(offerDTO, Offer.class));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(offer);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") UUID id, @Valid @RequestBody OfferUpdateDTO offerDTO) {
        // on s'assure qu'il à bien le bon id
        Offer offer = modelMapper.map(offerDTO, Offer.class);
        offer.setOfferId(id);

        Offer updatedOffer = service.update(offer);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedOffer);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteOffer(@PathVariable UUID id) {

        var isRemoved = this.service.delete(id);

        if (!isRemoved) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Offer not found");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(id);
    }


}
