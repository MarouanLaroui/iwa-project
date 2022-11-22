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

    @Column()
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name="employees_number")
    private String employeesNumber;

    @Column()
    private String description;

    @Column()
    private SectorType sector;

    @Column(name="picture_url")
    private String pictureUrl;
}
