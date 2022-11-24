package fr.polytech.ig5.mnm.userms.DTO;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
public class WorkerUpdateDTO {

    private String firstName;

    private String lastName;

    @Email
    private String email;

    private String password;

    private LocalDate birthDate;

    private String cvLink;

    private Boolean hasDrivingLicense;

    private Boolean hasCar;
}
