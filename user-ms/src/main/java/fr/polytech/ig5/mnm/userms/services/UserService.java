package fr.polytech.ig5.mnm.userms.services;

import fr.polytech.ig5.mnm.userms.models.User;
import fr.polytech.ig5.mnm.userms.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        // TODO: find better alternative to type cast
        return (List<User>) this.repository.findAll();
    }

    public User create(User user) {
        return this.repository.save(user);
    }
}
