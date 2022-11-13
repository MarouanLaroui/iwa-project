package fr.polytech.ig5.mnm.userms;

import fr.polytech.ig5.mnm.userms.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("fr.polytech.ig5.mnm.userms.repositories")
@EntityScan("fr.polytech.ig5.mnm.userms.models")
@SpringBootApplication
public class UserMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserMsApplication.class, args);
    }
}
