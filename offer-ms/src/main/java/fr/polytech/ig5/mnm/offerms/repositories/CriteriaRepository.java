package fr.polytech.ig5.mnm.offerms.repositories;

import fr.polytech.ig5.mnm.offerms.models.Application;
import fr.polytech.ig5.mnm.offerms.models.Criteria;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CriteriaRepository extends CrudRepository<Criteria, UUID> {
    void deleteByWorkerId(UUID workerId);

    Optional<Criteria> findCriteriaByWorkerId(UUID workerId);
}
