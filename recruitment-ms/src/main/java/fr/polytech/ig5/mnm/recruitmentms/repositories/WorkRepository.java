package fr.polytech.ig5.mnm.recruitmentms.repositories;

import fr.polytech.ig5.mnm.recruitmentms.models.Work;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository extends CrudRepository<Work, Long> {

}
