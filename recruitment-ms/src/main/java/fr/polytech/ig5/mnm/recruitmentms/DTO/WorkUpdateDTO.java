package fr.polytech.ig5.mnm.recruitmentms.DTO;

import lombok.Data;
import java.sql.Date;

@Data
public class WorkUpdateDTO {

    private Long id;
    private Long companyId;
    private Long workerId;
    private String jobLabel;
    private Date startingDate;
    private Date end_date;
    private Boolean isRatedByEmployee;
    private Boolean isRatedByCompany;

}
