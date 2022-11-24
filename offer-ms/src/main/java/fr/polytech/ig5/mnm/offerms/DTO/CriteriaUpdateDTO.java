package fr.polytech.ig5.mnm.offerms.DTO;

import fr.polytech.ig5.mnm.offerms.models.JobType;
import fr.polytech.ig5.mnm.offerms.models.SectorType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CriteriaUpdateDTO {

    // criteriaId will come from a route parameter

    // workerId will come from JWT

    @NotEmpty(message = "a criteria must have a contract type")
    private String contractType;

    @NotNull(message = "a criteria must have a job type")
    private JobType jobType;

    @NotNull(message = "a criteria must have a sector")
    private SectorType sector;

    @NotNull(message = "a criteria must have a salary expectation")
    private int salaryExpectation;

    @NotNull(message = "a criteria must have a starting date (YYYY-MM-DD)")
    private LocalDate startingDate;

    @NotNull(message = "a criteria must have a end date (YYYY-MM-DD)")
    private LocalDate endDate;

    @NotEmpty(message = "a criteria must have a location")
    private String location;
}
