package fr.polytech.ig5.mnm.userms.DTO;

import lombok.Data;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class WorkerAuthenticatedDTO {

    private UUID id;

    private String firstname;

    private String lastname;

    @Email
    private String email;

    private LocalDate birthDate;

    private String cvLink;

    private Boolean hasDrivingLicense;

    private String authorizationToken;

    private String pictureUrl;

}
