package fr.polytech.ig5.mnm.offerms.DTO;

import lombok.Data;

import java.sql.Date;

@Data
public class CriteriaUpdateDTO {

    private Long criteriaId;
    private Long workerId;
    private String contractType;
    private String sector;
    private String jobType;
    private int salaryExpectation;
    private Date startingDate;
    private Date endDate;
    private String location;

}
