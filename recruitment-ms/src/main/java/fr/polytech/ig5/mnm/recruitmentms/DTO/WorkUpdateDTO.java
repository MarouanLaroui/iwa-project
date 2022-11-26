package fr.polytech.ig5.mnm.recruitmentms.DTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class WorkUpdateDTO {

    @NotEmpty(message = "a work must have a job label")
    private String jobLabel;

    @NotNull(message = "a work must have a starting date (YYYY-MM-DD)")
    private LocalDate startingDate;

    @NotNull(message = "a work must have a end date (YYYY-MM-DD)")
    private LocalDate endDate;

    private Boolean isRatedByEmployee;

    private Boolean isRatedByCompany;

}
