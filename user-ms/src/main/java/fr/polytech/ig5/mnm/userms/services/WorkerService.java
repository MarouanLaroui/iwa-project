package fr.polytech.ig5.mnm.userms.services;

import fr.polytech.ig5.mnm.userms.models.Worker;
import fr.polytech.ig5.mnm.userms.repositories.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepository repository;

    public WorkerService(WorkerRepository repository) {
        this.repository = repository;
    }

    public List<Worker> findAll() {
        // TODO: find better alternative to type cast
        return (List<Worker>) this.repository.findAll();
    }

    public Optional<Worker> find(final Long id) {
        return repository.findById(id);
    }

    public Worker create(Worker user) {
        return this.repository.save(user);
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
