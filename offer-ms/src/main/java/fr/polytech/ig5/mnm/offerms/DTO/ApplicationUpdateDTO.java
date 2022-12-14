package fr.polytech.ig5.mnm.offerms.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class ApplicationUpdateDTO {

    // applicationId will be given as a route parameter

    @NotNull(message = "missing isValidatedByCompany")
    private Boolean isValidatedByCompany;

    @NotNull(message = "missing isValidatedByWorker")
    private Boolean isValidatedByWorker;

}
