package fr.polytech.ig5.mnm.offerms.repositories;

import fr.polytech.ig5.mnm.offerms.models.Offer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OfferRepository extends CrudRepository<Offer, UUID> {

}
