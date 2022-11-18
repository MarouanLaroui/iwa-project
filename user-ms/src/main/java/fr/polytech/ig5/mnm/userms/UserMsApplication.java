package fr.polytech.ig5.mnm.userms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("fr.polytech.ig5.mnm.userms.repositories")
@EntityScan("fr.polytech.ig5.mnm.userms.models")
@SpringBootApplication
@Slf4j
public class UserMsApplication {

    public static void main(String[] args) {
        log.info("***************** HERE ************************");
        SpringApplication.run(UserMsApplication.class, args);
    }
}
