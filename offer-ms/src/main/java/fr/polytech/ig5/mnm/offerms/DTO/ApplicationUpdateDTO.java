package fr.polytech.ig5.mnm.offerms.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ApplicationUpdateDTO {

    private Long applicationId;

    private String message;

    @NotNull(message = "missing isValidatedByCompany")
    private Boolean isValidatedByCompany;

    @NotNull(message = "missing isValidatedByWorker")
    private Boolean isValidatedByWorker;

}
