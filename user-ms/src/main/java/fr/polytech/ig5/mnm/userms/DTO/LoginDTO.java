package fr.polytech.ig5.mnm.userms.DTO;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginDTO {

    @NotEmpty(message = "a user must have an email")
    @Email
    private String email;

    @NotEmpty(message = "a user must have a password")
    private String password;
}
