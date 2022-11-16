package fr.polytech.ig5.mnm.offerms.offerms.services;
import fr.polytech.ig5.mnm.offerms.offerms.models.Offer;
import fr.polytech.ig5.mnm.offerms.offerms.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Offer> find(final Long id) {
        return repository.findById(id);
    }

    public Offer create(Offer user) {
        return this.repository.save(user);
    }

    public Boolean delete(final Long id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
