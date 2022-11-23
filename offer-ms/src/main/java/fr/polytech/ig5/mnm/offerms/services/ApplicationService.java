package fr.polytech.ig5.mnm.offerms.services;
import fr.polytech.ig5.mnm.offerms.models.Application;
import fr.polytech.ig5.mnm.offerms.repositories.ApplicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Optional<Application> find(final UUID id) {
        return repository.findById(id);
    }

    public Application create(Application application) {
        return this.repository.save(application);
    }

    public Application update(Application application) {
        return this.repository.save(application);
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

    @Transactional
    public Boolean deleteByWorkerId(UUID workerId){
        try{
            this.repository.deleteByWorkerId(workerId);
            return true;
        }
        catch (Exception e){
            Logger logger = LoggerFactory.getLogger(ApplicationService.class);
            logger.warn("Failed to delete offer associated from APPLICATION_DELETED TOPIC " + e.getMessage());
            return false;
        }
    }
}
