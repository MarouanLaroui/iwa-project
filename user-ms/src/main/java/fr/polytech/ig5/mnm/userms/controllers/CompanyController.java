package fr.polytech.ig5.mnm.userms.controllers;

import fr.polytech.ig5.mnm.userms.DTO.*;
import fr.polytech.ig5.mnm.userms.models.Company;
import fr.polytech.ig5.mnm.userms.services.CompanyService;
import fr.polytech.ig5.mnm.userms.utils.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

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

    @PostMapping("/register")
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyCreateDTO companyCreateDTO) {
        companyCreateDTO.setPassword(passwordEncoder.encode(companyCreateDTO.getPassword()));
        Company company = modelMapper.map(companyCreateDTO, Company.class);

        CompanyAuthenticatedDTO companyCreated =
                modelMapper.map(service.create(company), CompanyAuthenticatedDTO.class);

        Map<String, Object> claims = new HashMap<>();
        claims.put("companyId", company.getId());
        String token = jwtUtils.createJWT(claims, 1 * 60 * 60 * 1000);

        companyCreated.setAuthorizationToken(token);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(companyCreated);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDTO loginDTO) {

        String email = loginDTO.getEmail();

        Optional<Company> optionalCompany = this.service.findByEmail(email);
        if (optionalCompany.isEmpty()) return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("This account does not exist");

        Company company = optionalCompany.get();

        Map<String, Object> claims = new HashMap<>();
        claims.put("companyId", company.getId());

        if (passwordEncoder.matches(loginDTO.getPassword(), company.getPassword())) {
            String token = jwtUtils.createJWT(claims, 1 * 60 * 60 * 1000);
            CompanyAuthenticatedDTO companyAuthenticated =
                    modelMapper.map(service.create(company), CompanyAuthenticatedDTO.class);
            companyAuthenticated.setAuthorizationToken(token);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(companyAuthenticated);
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Wrong password");
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

    @DeleteMapping(value = "/")
    public ResponseEntity<Object> delete(@RequestHeader (name="Authorization") String bearerToken) {
        System.out.println(bearerToken);
        UUID companyId = jwtUtils.extractUUIDFromJWT("companyId", bearerToken);
        Boolean isRemoved = this.service.delete(companyId);

        if(!isRemoved){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Company not found");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(companyId);
    }

}
