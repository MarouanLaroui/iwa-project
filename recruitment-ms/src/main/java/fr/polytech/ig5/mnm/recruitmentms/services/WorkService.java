package fr.polytech.ig5.mnm.recruitmentms.services;

import fr.polytech.ig5.mnm.recruitmentms.models.Work;
import fr.polytech.ig5.mnm.recruitmentms.repositories.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class WorkService {

    @Autowired
    private WorkRepository repository;

    public WorkService(WorkRepository repository) {
        this.repository = repository;
    }

    public List<Work> findAll() {
        // TODO: find better alternative to type cast
        return (List<Work>) this.repository.findAll();
    }

    public Optional<Work> find(final Long id) {
        return repository.findById(id);
    }

    public Work create(Work work) {
        return this.repository.save(work);
    }

    public Work update(Work work) {
        return this.repository.save(work);
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
