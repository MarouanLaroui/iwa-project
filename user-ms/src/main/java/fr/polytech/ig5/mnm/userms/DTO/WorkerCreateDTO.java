package fr.polytech.ig5.mnm.userms.DTO;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class WorkerCreateDTO {

    @NotEmpty(message = "a worker must have a firstname")
    private String firstname;

    @NotEmpty(message = "a worker must have a lastname")
    private String lastname;

    @NotEmpty(message = "a worker must have an email")
    @Email
    private String email;

    @NotEmpty(message = "a worker must have a password")
    private String password;

    @NotNull(message = "a worker must have a birth date")
    private LocalDate birthDate;

    private String cvLink;

    @NotNull(message = "a worker must specify if he has his driving license")
    private Boolean hasDrivingLicense;

    private String pictureUrl;

}
