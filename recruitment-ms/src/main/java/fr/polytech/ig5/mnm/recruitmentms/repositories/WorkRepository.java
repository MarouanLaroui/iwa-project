package fr.polytech.ig5.mnm.recruitmentms.repositories;

import fr.polytech.ig5.mnm.recruitmentms.models.Work;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkRepository extends CrudRepository<Work, UUID> {
    void deleteWorksByCompanyId(UUID companyId);

    void deleteWorksByWorkerId(UUID workerId);

    List<Work> findWorksByCompanyId(UUID companyId);

    List<Work> findWorksByWorkerId(UUID workerId);
}
