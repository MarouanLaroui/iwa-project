package fr.polytech.ig5.mnm.userms.models;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name="companies")
public class Company {

    @Id
    @GeneratedValue()
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name="employees_number", nullable = false)
    private String employeesNumber;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private SectorType sector;

    @Column(name="picture_url")
    private String pictureUrl;
}
