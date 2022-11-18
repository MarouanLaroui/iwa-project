package fr.polytech.ig5.mnm.offerms.controllers;

import fr.polytech.ig5.mnm.offerms.DTO.OfferCreateDTO;
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
    public List<Offer> index() {
        return this.service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Offer> get(@PathVariable("id") Long id) {
        return this.service.find(id);
    }

    @PostMapping("/")
    public Offer create(@Valid @RequestBody OfferCreateDTO offerDTO) {
        return this.service.create(modelMapper.map(offerDTO, Offer.class));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteOffer(@PathVariable Long id) {

        var isRemoved = this.service.delete(id);

        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // doesn't work, empty return in postman
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
