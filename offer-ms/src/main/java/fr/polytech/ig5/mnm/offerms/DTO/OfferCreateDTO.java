package fr.polytech.ig5.mnm.offerms.DTO;

import fr.polytech.ig5.mnm.offerms.models.ContractType;
import fr.polytech.ig5.mnm.offerms.models.JobType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class OfferCreateDTO {

    @NotNull(message = "an offer must have a company id")
    private UUID companyId;

    @NotEmpty(message = "an offer must have a title")
    private String title;

    @NotEmpty(message = "an offer must have a description")
    private String description;

    @NotEmpty(message = "an offer must have a location")
    private String location;

    @NotNull(message = "an offer must have a creation date (YYYY-MM-DD)")
    private LocalDate creationDate = LocalDate.now();

    @NotNull(message = "an offer must have a starting date (YYYY-MM-DD)")
    private LocalDate startingDate;

    @NotNull(message = "an offer must have an end date (YYYY-MM-DD)")
    private LocalDate endDate;

    @NotNull(message = "an offer must have a contract type")
    private ContractType contractType;

    @NotNull(message = "an offer must have a job type")
    private JobType jobType;

    @NotNull(message = "an offer must have a salary")
    private int salary;

    @NotNull(message = "an offer must specify if a driving license is required")
    private Boolean needDrivingLicence;

    private Boolean ledToJob = false;

}
