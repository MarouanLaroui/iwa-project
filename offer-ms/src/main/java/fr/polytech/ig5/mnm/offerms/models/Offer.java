package fr.polytech.ig5.mnm.offerms.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name="offers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Offer {

    @Id
    @GeneratedValue()
    private UUID offerId;

    @Column(name = "company_id", nullable = false)
    private UUID companyId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String location;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "starting_date", nullable = false)
    private LocalDate startingDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "contract_type", nullable = false)
    private ContractType contractType;

    @Column(name = "job_type", nullable = false)
    private JobType jobType;

    @Column(nullable = false)
    private int salary;

    @Column(name = "need_driving_license", nullable = false)
    private Boolean needDrivingLicence;

    @Column(name = "led_to_job", nullable = false)
    private Boolean ledToJob;

    public Offer(String title, String location, LocalDate startingDate, LocalDate endDate, ContractType contractType, JobType jobType, int salary) {
        this.title = title;
        this.location = location;
        this.startingDate = startingDate;
        this.endDate = endDate;
        this.contractType = contractType;
        this.jobType = jobType;
        this.salary = salary;
    }
}
