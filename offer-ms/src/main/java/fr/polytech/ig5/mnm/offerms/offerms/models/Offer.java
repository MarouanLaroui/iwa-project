package fr.polytech.ig5.mnm.offerms.offerms.models;

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
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(name="creation_date")
    private Date creationDate;

    @Column(name="starting_date")
    private Date startingDate;

    @Column(name="end_date")
    private Date endDate;

    @Column(nullable = false, name = "contract_type")
    private ContractType contractType;

    @Column()
    private int salary;

    @Column(name = "nb_of_hours")
    private int nbOfHours;

    @Column(name="need_driving_license")
    private Boolean needDrivingLicence;

    @Column(name="led_to_job")
    private Boolean ledToJob;

    public Offer() {}
}
