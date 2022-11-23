package fr.polytech.ig5.mnm.userms.repositories;

import fr.polytech.ig5.mnm.userms.models.Worker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkerRepository extends CrudRepository<Worker, UUID> {

    Optional<Worker> findByEmail(String email);
}
