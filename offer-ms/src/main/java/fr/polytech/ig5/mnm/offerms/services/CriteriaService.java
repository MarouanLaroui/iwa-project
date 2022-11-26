package fr.polytech.ig5.mnm.offerms.services;
import fr.polytech.ig5.mnm.offerms.models.Criteria;
import fr.polytech.ig5.mnm.offerms.repositories.CriteriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CriteriaService{
    @Autowired
    private CriteriaRepository repository;

    public CriteriaService(CriteriaRepository repository) {
        this.repository = repository;
    }

    public List<Criteria> findAll() {
        // TODO: find better alternative to type cast
        return (List<Criteria>) this.repository.findAll();
    }

    public Optional<Criteria> find(final UUID id) {
        return repository.findById(id);
    }

    public Criteria create(Criteria criteria) {
        return this.repository.save(criteria);
    }

    public Criteria update(Criteria criteria) {
        return this.repository.save(criteria);
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
            System.out.println("delete criteria");
            this.repository.deleteByWorkerId(workerId);
            return true;
        } catch (Exception e){
            Logger logger = LoggerFactory.getLogger(CriteriaService.class);
            logger.warn("Failed to delete criteria associated from WORKER_DELETED TOPIC " + e.getMessage());
            return false;
        }
    }

    public Optional<Criteria> findCriteriaByWorkerId(UUID workerId){
        return this.repository.findCriteriaByWorkerId(workerId);
    }
}
