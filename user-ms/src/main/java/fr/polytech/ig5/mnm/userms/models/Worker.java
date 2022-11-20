package fr.polytech.ig5.mnm.userms.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name="workers")
public class Worker {

    @Id
    @GeneratedValue()
    private UUID id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(name="birth_date")
    private LocalDate birthDate;

    @Column(name="cv_link")
    private String cvLink;

    @Column(name="has_driving_license")
    private Boolean hasDrivingLicense;

}