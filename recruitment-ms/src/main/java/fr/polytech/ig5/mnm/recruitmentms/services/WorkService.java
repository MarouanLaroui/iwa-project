package fr.polytech.ig5.mnm.recruitmentms.services;

import fr.polytech.ig5.mnm.recruitmentms.models.Work;
import fr.polytech.ig5.mnm.recruitmentms.repositories.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class WorkService {

    @Autowired
    private WorkRepository repository;

    public WorkService(WorkRepository repository) {
        this.repository = repository;
    }

    public List<Work> findAll() {
        // TODO: find better alternative to type cast
        return (List<Work>) this.repository.findAll();
    }

    public Optional<Work> find(final UUID id) {
        return repository.findById(id);
    }

    public Work create(Work work) {
        return this.repository.save(work);
    }

    public Work update(Work work) {
        return this.repository.save(work);
    }

    public Boolean delete(final UUID id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Transactional
    public Boolean deleteByWorkerId(UUID workerId){
        try{
            this.repository.deleteByWorkerId(workerId);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @Transactional
    public Boolean deleteByCompanyId(UUID companyId){
        try{
            this.repository.deleteByCompanyId(companyId);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
