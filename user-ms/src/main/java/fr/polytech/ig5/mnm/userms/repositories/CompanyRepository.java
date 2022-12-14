package fr.polytech.ig5.mnm.userms.repositories;

import fr.polytech.ig5.mnm.userms.models.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends CrudRepository<Company, UUID> {

    Optional<Company> findCompanyByEmail(String email);
}
