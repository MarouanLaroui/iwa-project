package fr.polytech.ig5.mnm.offerms.DTO;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Data
public class ApplicationCreateDTO {

    private String message;

    private Boolean isValidatedByCompany = false;

    private Boolean isValidatedByWorker = false;

    // envoyer dans le token plus tard? donc pas besoin de verif dans le DTO
    private Long workerId;

    //private Long offerId;

}
