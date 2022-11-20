package fr.polytech.ig5.mnm.offerms.DTO;

import fr.polytech.ig5.mnm.offerms.models.ContractType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class OfferUpdateDTO {

    @NotNull(message = "to be updated an offer must have an id")
    private UUID offerId;

    @NotEmpty(message = "an offer must have a description")
    private String description;

    @NotNull(message = "an offer must have a starting date (YYYY-MM-DD)")
    private LocalDate startingDate;

    @NotNull(message = "an offer must have a end date (YYYY-MM-DD)")
    private LocalDate endDate;

    @NotNull(message = "an offer must have a contract type")
    private ContractType contractType;

    @NotNull(message = "an offer must have a location")
    private String location;

    private int salary;

    private int nbOfHours;

    private Boolean needDrivingLicence;

    private Boolean ledToJob;

}
