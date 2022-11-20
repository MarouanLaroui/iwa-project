package fr.polytech.ig5.mnm.userms.controllers;

import fr.polytech.ig5.mnm.userms.DTO.CompanyCreateDTO;
import fr.polytech.ig5.mnm.userms.DTO.CompanyUpdateDTO;
import fr.polytech.ig5.mnm.userms.DTO.WorkerUpdateDTO;
import fr.polytech.ig5.mnm.userms.models.Company;
import fr.polytech.ig5.mnm.userms.models.Worker;
import fr.polytech.ig5.mnm.userms.services.CompanyService;
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
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<Object> index() {
        List<Company> companies = this.service.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(companies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable("id") UUID id) {
        Optional<Company> company = service.find(id);

        if(company.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Company not found");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(company);
    }

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyCreateDTO companyDTO) {
        Company company = modelMapper.map(companyDTO, Company.class);
        Company companyCreated = service.create(company);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(companyCreated);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> update(@PathVariable("id") UUID id,@Valid @RequestBody CompanyUpdateDTO companyDTO) {
        Company company = modelMapper.map(companyDTO, Company.class);
        company.setId(id);

        Company updatedCompany =
                service.update(company);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedCompany);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable UUID id) {
        Boolean isRemoved = this.service.delete(id);

        if(!isRemoved){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Company not found");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(id);
    }

}
