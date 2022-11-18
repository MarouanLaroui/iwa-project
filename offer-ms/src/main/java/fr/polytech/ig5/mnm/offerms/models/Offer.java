package fr.polytech.ig5.mnm.offerms.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import java.sql.Date;
import java.util.Set;

@Data
@Entity
@Table(name="offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offerId;

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

    @Column
    private String location;

    @Column(name = "nb_of_hours")
    private int nbOfHours;

    @Column(name="need_driving_license")
    private Boolean needDrivingLicence;

    @Column(name="led_to_job")
    private Boolean ledToJob;

}
