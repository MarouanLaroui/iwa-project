package fr.polytech.ig5.mnm.userms.DTO;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
public class WorkerUpdateDTO {

    @NotEmpty(message = "a worker must have a firstname")
    private String firstName;

    @NotEmpty(message = "a worker must have a lastname")
    private String lastName;

    @NotEmpty(message = "a worker must have an email")
    @Email
    private String email;

    private LocalDate birthDate;
    private String cvLink;
    private Boolean hasDrivingLicense;
}
