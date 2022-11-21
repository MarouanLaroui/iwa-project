package fr.polytech.ig5.mnm.userms.DTO;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class WorkerLoginDTO {

    @NotEmpty(message = "a worker must have an email")
    @Email
    private String email;

    @NotEmpty(message = "a worker must have a password")
    private String password;
}
