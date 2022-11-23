package fr.polytech.ig5.mnm.offerms.repositories;

import fr.polytech.ig5.mnm.offerms.models.Application;
import fr.polytech.ig5.mnm.offerms.models.Offer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApplicationRepository extends CrudRepository<Application, UUID> {

    List<Application> findApplicationByWorkerId(UUID workerId);

    List<Application> findApplicationByOffer(Offer offer);

    UUID deleteByWorkerId(UUID workerId);
}
