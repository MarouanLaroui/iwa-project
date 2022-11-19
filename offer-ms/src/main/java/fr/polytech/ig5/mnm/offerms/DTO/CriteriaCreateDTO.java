package fr.polytech.ig5.mnm.offerms.DTO;


import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
public class CriteriaCreateDTO {

    private UUID workerId;
    private String contractType;
    private String sector;
    private String jobType;
    private int salaryExpectation;
    private Date startingDate;
    private Date endDate;
    private String location;

}
