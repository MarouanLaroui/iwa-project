package fr.polytech.ig5.mnm.recruitmentms.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name="workers")
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="company_id")
    private Long companyId;

    @Column(name="worker_id")
    private Long workerId;

    @Column(name="job_label")
    private String jobLabel;

    @Column(name="starting_date")
    private Date startingDate;

    @Column(name="end_date")
    private Date end_date;

    @Column(name="is_rated_by_employee")
    private Boolean isRatedByEmployee;

    @Column(name="is_rated_by_company")
    private Boolean isRatedByCompany;

    public Work(Long companyId, Long workerId, String jobLabel, Date startingDate, Date end_date) {
        this.companyId = companyId;
        this.workerId = workerId;
        this.jobLabel = jobLabel;
        this.startingDate = startingDate;
        this.end_date = end_date;
        this.isRatedByEmployee = false;
        this.isRatedByCompany = false;
    }

    public Work() {}
}