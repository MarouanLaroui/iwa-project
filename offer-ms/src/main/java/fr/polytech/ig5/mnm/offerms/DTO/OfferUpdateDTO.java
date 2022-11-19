package fr.polytech.ig5.mnm.offerms.DTO;

import fr.polytech.ig5.mnm.offerms.models.ContractType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
public class OfferUpdateDTO {

    @NotNull(message = "to be updated an offer must have an id")
    private Long offerId;

    @NotEmpty(message = "an offer must have a description")
    private String description;

    // we need to create this field automatically during the creation
    /*
    @NotNull(message = "an offer must have a creation date (YYYY-MM-DD)")
    private Date creationDate;
     */

    @NotNull(message = "an offer must have a starting date (YYYY-MM-DD)")
    private Date startingDate;

    @NotNull(message = "an offer must have a end date (YYYY-MM-DD)")
    private Date endDate;

    @NotNull(message = "an offer must have a contract type")
    private ContractType contractType;

    //@NotNull(message = "an offer must have a salary")
    private int salary;

    @NotNull(message = "an offer must have a location")
    private String location;

    //@NotNull(message = "an offer must have a number of hours")
    private int nbOfHours;

    private Boolean needDrivingLicence;

    private Boolean ledToJob;

}
