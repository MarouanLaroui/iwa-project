package fr.polytech.ig5.mnm.offerms.offerms.services;
import fr.polytech.ig5.mnm.offerms.offerms.models.Application;
import fr.polytech.ig5.mnm.offerms.offerms.models.Offer;
import fr.polytech.ig5.mnm.offerms.offerms.repositories.ApplicationRepository;
import fr.polytech.ig5.mnm.offerms.offerms.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService{
    @Autowired
    private ApplicationRepository repository;

    public ApplicationService(ApplicationRepository repository) {
        this.repository = repository;
    }

    public List<Application> findAll() {
        // TODO: find better alternative to type cast
        return (List<Application>) this.repository.findAll();
    }

    public Optional<Application> find(final Long id) {
        return repository.findById(id);
    }

    public Application create(Application application) {
        return this.repository.save(application);
    }

    public Boolean delete(final Long id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
