package fr.polytech.ig5.mnm.recruitmentms.repositories;

import fr.polytech.ig5.mnm.recruitmentms.models.Work;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkRepository extends CrudRepository<Work, UUID> {
    UUID deleteByCompanyId(UUID companyId);
    UUID deleteByWorkerId(UUID workerId);
}
