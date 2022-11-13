package fr.polytech.ig5.mnm.userms.controllers;

import fr.polytech.ig5.mnm.userms.models.User;
import fr.polytech.ig5.mnm.userms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public User create() {
        return this.service.create(new User("Nathan", "Djian-Martin", "nathan@gmail.com"));
    }

    @GetMapping("/")
    public List<User> index() {
        return this.service.findAll();
    }
}
