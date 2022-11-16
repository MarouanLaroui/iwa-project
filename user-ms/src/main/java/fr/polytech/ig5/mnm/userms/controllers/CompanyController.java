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
    public List<Company> index() {
        return this.service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Company> get(@PathVariable("id") Long id) {
        return this.service.find(id);
    }

    @PostMapping("/create")
    public Company create(@RequestBody Company worker) {
        return this.service.create(worker);
    }

    @DeleteMapping(value = "/{id}")
    public Boolean deletePost(@PathVariable Long id) {
        return this.service.delete(id);
    }

}
