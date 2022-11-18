package fr.polytech.ig5.mnm.recruitmentms.DTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
public class WorkCreateDTO {

    private Long id;

    @NotNull(message = "a work must be linked to a company")
    private Long companyId;

    @NotNull(message = "a work must be linked to a worker")
    private Long workerId;

    @NotEmpty(message = "a work must have a job label")
    private String jobLabel;

    @NotNull(message = "a work must have a starting date (YYYY-MM-DD)")
    private Date startingDate;

    @NotNull(message = "a work must have a end date (YYYY-MM-DD)")
    private Date endDate;

    private Boolean isRatedByEmployee;

    private Boolean isRatedByCompany;

}
