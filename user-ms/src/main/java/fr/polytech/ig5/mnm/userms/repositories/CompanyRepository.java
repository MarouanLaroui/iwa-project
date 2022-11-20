package fr.polytech.ig5.mnm.userms.repositories;

import fr.polytech.ig5.mnm.userms.models.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {

}
