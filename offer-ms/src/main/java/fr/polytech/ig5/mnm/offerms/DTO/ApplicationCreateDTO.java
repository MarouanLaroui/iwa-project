package fr.polytech.ig5.mnm.offerms.DTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ApplicationCreateDTO {

    // offerId is given as a route param

    // workerId is given in the JWT

    @NotEmpty(message = "an application must have a message")
    private String message;

    private Boolean isValidatedByCompany = false;

    private Boolean isValidatedByWorker = false;
}
