package fr.polytech.ig5.mnm.recruitmentms.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Data
@Entity
@Table(name="works")
public class Work {

    @Id
    @GeneratedValue()
    private UUID workId;

    @Column(name="company_id")
    private UUID companyId;

    @Column(name="worker_id")
    private UUID workerId;

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

}