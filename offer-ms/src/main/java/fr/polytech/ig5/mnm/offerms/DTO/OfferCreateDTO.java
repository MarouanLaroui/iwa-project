package fr.polytech.ig5.mnm.offerms.DTO;

import fr.polytech.ig5.mnm.offerms.models.ContractType;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

@Data
public class OfferCreateDTO {

    @NotEmpty(message = "an offer must have a description")
    private String description;

    private UUID companyId;

    @CreatedDate
    private Instant creationDate;

    @NotNull(message = "an offer must have a starting date (YYYY-MM-DD)")
    private Date startingDate;

    @NotNull(message = "an offer must have a end date (YYYY-MM-DD)")
    private Date endDate;

    @NotNull(message = "an offer must have a contract type")
    private ContractType contractType;

    @NotNull(message = "an offer must have a location")
    private String location;

    private int salary;

    private int nbOfHours;

    private Boolean needDrivingLicence;

    private Boolean ledToJob;

}
