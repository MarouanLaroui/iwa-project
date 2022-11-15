package fr.polytech.ig5.mnm.userms.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import java.sql.Date;

@Data
@Entity
@Table(name="workers")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(name="birth_date")
    private Date birthDate;

    @Column(name="cv_link")
    private String cvLink;

    @Column(name="has_driving_license")
    private Boolean hasDrivingLicense;

    public Worker(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Worker(String firstName, String lastName, String email, Date birthDate, String cvLink, Boolean hasDrivingLicense) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.cvLink = cvLink;
        this.hasDrivingLicense = hasDrivingLicense;
    }

    public Worker() {}
}