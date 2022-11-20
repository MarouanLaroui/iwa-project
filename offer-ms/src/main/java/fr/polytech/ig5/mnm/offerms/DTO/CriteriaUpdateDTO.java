package fr.polytech.ig5.mnm.offerms.DTO;

import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CriteriaUpdateDTO {

    private UUID criteriaId;
    private String contractType;
    private String sector;
    private String jobType;
    private int salaryExpectation;
    private LocalDate startingDate;
    private LocalDate endDate;
    private String location;

}
