package fr.polytech.ig5.mnm.offerms.models;

import javax.persistence.*;

import lombok.Data;
import java.sql.Date;
import java.util.UUID;

@Data
@Entity
@Table(name="criterias")
public class Criteria {

    @Id
    @GeneratedValue()
    private UUID criteriaId;

    @Column(name="worker_id", nullable = false)
    private UUID workerId;

    @Column(name="contract_type")
    private String contractType;

    @Column(name="sector")
    private String sector;

    @Column(name="job_type")
    private String jobType;

    @Column(name="salary_expectation")
    private int salaryExpectation;

    @Column(name="starting_date")
    private Date startingDate;

    @Column(name="end_date")
    private Date endDate;

    @Column
    private String location;
}
