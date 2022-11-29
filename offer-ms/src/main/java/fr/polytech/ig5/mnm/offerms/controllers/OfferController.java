package fr.polytech.ig5.mnm.offerms.controllers;

import fr.polytech.ig5.mnm.offerms.utils.JwtUtils;
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
    JwtUtils jwtUtils;

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
    public ResponseEntity<Object> create(
            @Valid @RequestBody OfferCreateDTO offerDTO,
            @RequestHeader (name="Authorization") String bearerToken) {

        UUID companyId = jwtUtils.extractUUIDFromJWT("companyId", bearerToken);
        Offer offer = modelMapper.map(offerDTO, Offer.class);
        offer.setCompanyId(companyId);

        Offer offerCreated = this.service.create(offer);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(offerCreated);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> update(
            @PathVariable("id") UUID id,
            @Valid @RequestBody OfferUpdateDTO offerDTO,
            @RequestHeader (name="Authorization") String bearerToken) {
        // on verifie que la company à les droits de modification
        UUID companyId = jwtUtils.extractUUIDFromJWT("companyId", bearerToken);
        Optional<Offer> optionalOffer = this.service.find(id);

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

        Offer newOffer = modelMapper.map(offerDTO, Offer.class);

        // immutables fields
        newOffer.setOfferId(offer.getOfferId());
        newOffer.setCompanyId(offer.getCompanyId());
        newOffer.setCreationDate(offer.getCreationDate());

        Offer updatedOffer = service.update(newOffer);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedOffer);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteOffer(
            @PathVariable UUID id,
            @RequestHeader (name="Authorization") String bearerToken) {

        UUID companyId = jwtUtils.extractUUIDFromJWT("companyId", bearerToken);
        Optional<Offer> optionalOffer = this.service.find(id);

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

        var isRemoved = this.service.delete(offer);

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
