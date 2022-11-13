package fr.polytech.ig5.mnm.userms.repositories;

import fr.polytech.ig5.mnm.userms.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
