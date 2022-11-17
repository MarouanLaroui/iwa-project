package fr.polytech.ig5.mnm.offerms.services;
import fr.polytech.ig5.mnm.offerms.models.Criteria;
import fr.polytech.ig5.mnm.offerms.repositories.CriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Criteria> find(final Long id) {
        return repository.findById(id);
    }

    public Criteria create(Criteria criteria) {
        return this.repository.save(criteria);
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
