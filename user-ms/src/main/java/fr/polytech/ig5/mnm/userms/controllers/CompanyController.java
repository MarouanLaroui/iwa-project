package fr.polytech.ig5.mnm.userms.controllers;

import fr.polytech.ig5.mnm.userms.models.Company;
import fr.polytech.ig5.mnm.userms.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {

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
    public ResponseEntity<Object> get(@PathVariable("id") Long id) {
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
    public ResponseEntity<Object> create(@RequestBody Company company) {
        //Company work = modelMapper.map(workDTO, Work.class);
        Company companyCreated = service.create(company);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(companyCreated);    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable Long id) {
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
