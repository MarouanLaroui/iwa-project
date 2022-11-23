package fr.polytech.ig5.mnm.userms.DTO;

import lombok.Data;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class WorkerGetDTO {

    private UUID id;

    private String firstName;

    private String lastName;

    @Email
    private String email;

    private LocalDate birthDate;

    private String cvLink;

    private Boolean hasDrivingLicense;
}
