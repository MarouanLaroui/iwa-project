package fr.polytech.ig5.mnm.recruitmentms.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name="works")
public class Work {

    @Id
    @GeneratedValue()
    private UUID workId;

    @Column(name="company_id", nullable = false)
    private UUID companyId;

    @Column(name="worker_id", nullable = false)
    private UUID workerId;

    @Column(name="job_label", nullable = false)
    private String jobLabel;

    @Column(name="starting_date", nullable = false)
    private LocalDate startingDate;

    @Column(name="end_date", nullable = false)
    private LocalDate endDate;

    @Column(name="is_rated_by_employee", nullable = false)
    private Boolean isRatedByEmployee;

    @Column(name="is_rated_by_company", nullable = false)
    private Boolean isRatedByCompany;

}