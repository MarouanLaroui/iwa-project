package fr.polytech.ig5.mnm.offerms.offerms.repositories;

import fr.polytech.ig5.mnm.offerms.offerms.models.Application;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends CrudRepository<Application, Long> {

}
