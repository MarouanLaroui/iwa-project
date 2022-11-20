package fr.polytech.ig5.mnm.userms.services;

import fr.polytech.ig5.mnm.userms.models.Company;
import fr.polytech.ig5.mnm.userms.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository repository;

    public CompanyService(CompanyRepository repository) {
        this.repository = repository;
    }

    public List<Company> findAll() {
        // TODO: find better alternative to type cast
        return (List<Company>) this.repository.findAll();
    }

    public Optional<Company> find(final UUID id) {
        return repository.findById(id);
    }

    public Company create(Company user) {
        return this.repository.save(user);
    }

    public Boolean delete(final UUID id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
