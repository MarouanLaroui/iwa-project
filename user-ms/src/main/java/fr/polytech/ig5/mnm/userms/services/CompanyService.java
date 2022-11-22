package fr.polytech.ig5.mnm.userms.services;

import fr.polytech.ig5.mnm.userms.kafka.KafkaProducer;
import fr.polytech.ig5.mnm.userms.models.Company;
import fr.polytech.ig5.mnm.userms.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CompanyService {

    private CompanyRepository repository;

    private KafkaProducer producer;

    @Autowired
    public CompanyService(CompanyRepository repository, KafkaProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    public List<Company> findAll() {
        return (List<Company>) this.repository.findAll();
    }

    public Optional<Company> find(final UUID id) {
        return repository.findById(id);
    }

    public Optional<Company> findByEmail(String email) {
        return this.repository.findCompanyByEmail(email);
    }

    public Company create(Company user) {
        return this.repository.save(user);
    }

    public Company update(Company user) {
        return this.repository.save(user);
    }

    public Boolean delete(final UUID id) {
        try {
            repository.deleteById(id);
            this.producer.sendMessage("COMPANY_DELETED", String.valueOf(id));
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
