package fr.polytech.ig5.mnm.offerms.models;

import javax.persistence.*;

import lombok.Data;
import java.time.LocalDate;
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

    @Column(name="job_label", nullable = false)
    private String jobLabel;

    @Column(name="contract_type", nullable = false)
    private ContractType contractType;

    @Column(name="job_type", nullable = false)
    private JobType jobType;

    @Column(nullable = false)
    private SectorType sector;

    @Column(name="salary_expectation", nullable = false)
    private int salaryExpectation;

    @Column(name="starting_date", nullable = false)
    private LocalDate startingDate;

    @Column(name="end_date", nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String location;

    public Criteria(String jobLabel, ContractType contractType, JobType jobType, SectorType sector, int salaryExpectation, LocalDate startingDate, LocalDate endDate, String location) {
        this.jobLabel = jobLabel;
        this.contractType = contractType;
        this.jobType = jobType;
        this.sector = sector;
        this.salaryExpectation = salaryExpectation;
        this.startingDate = startingDate;
        this.endDate = endDate;
        this.location = location;
    }
}
