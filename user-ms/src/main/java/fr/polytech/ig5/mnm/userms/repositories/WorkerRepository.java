package fr.polytech.ig5.mnm.userms.repositories;

import fr.polytech.ig5.mnm.userms.models.Worker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends CrudRepository<Worker, Long> {

}
