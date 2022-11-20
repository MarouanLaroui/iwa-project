package fr.polytech.ig5.mnm.userms.services;

import fr.polytech.ig5.mnm.userms.kafka.KafkaProducer;
import fr.polytech.ig5.mnm.userms.models.Worker;
import fr.polytech.ig5.mnm.userms.repositories.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkerService {

    private WorkerRepository repository;

    private KafkaProducer producer;

    @Autowired
    public WorkerService(WorkerRepository repository, KafkaProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    public List<Worker> findAll() {
        // TODO: find better alternative to type cast
        return (List<Worker>) this.repository.findAll();
    }

    public Optional<Worker> find(final UUID id) {
        return repository.findById(id);
    }

    public Worker create(Worker user) {
        return this.repository.save(user);
    }

    public Worker update(Worker user) {
        return this.repository.save(user);
    }

    public Boolean delete(final UUID id) {
        try {
            repository.deleteById(id);
            this.producer.sendMessage("USER_DELETED","coucou");
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
