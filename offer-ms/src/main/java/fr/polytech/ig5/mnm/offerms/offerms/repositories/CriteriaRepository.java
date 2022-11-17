package fr.polytech.ig5.mnm.offerms.offerms.repositories;

import fr.polytech.ig5.mnm.offerms.offerms.models.Criteria;
import fr.polytech.ig5.mnm.offerms.offerms.models.Offer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CriteriaRepository extends CrudRepository<Criteria, Long> {

}
