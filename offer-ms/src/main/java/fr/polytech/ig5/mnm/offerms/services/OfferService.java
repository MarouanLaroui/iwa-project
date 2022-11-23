package fr.polytech.ig5.mnm.offerms.services;
import fr.polytech.ig5.mnm.offerms.models.Offer;
import fr.polytech.ig5.mnm.offerms.repositories.OfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OfferService{
    @Autowired
    private OfferRepository repository;

    public OfferService(OfferRepository repository) {
        this.repository = repository;
    }

    public List<Offer> findAll() {
        // TODO: find better alternative to type cast
        return (List<Offer>) this.repository.findAll();
    }

    public Optional<Offer> find(final UUID id) {
        return repository.findById(id);
    }

    public Offer create(Offer offer) {
        return this.repository.save(offer);
    }

    public Offer update(Offer offer) {
        return this.repository.save(offer);
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
    public Boolean deleteByCompanyId(UUID workerId){
        try{
            this.repository.deleteByCompanyId(workerId);
            return true;
        }
        catch (Exception e){
            Logger logger = LoggerFactory.getLogger(OfferService.class);
            logger.warn("Failed to delete offer associated from COMPANY_DELETED TOPIC " + e.getMessage());
            return false;
        }
    }

    public List<Offer> findByCompanyId(UUID companyId) {
        return this.repository.findOfferByCompanyId(companyId);
    }
}
