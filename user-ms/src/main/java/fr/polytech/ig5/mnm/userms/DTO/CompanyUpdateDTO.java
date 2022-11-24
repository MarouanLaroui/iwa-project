package fr.polytech.ig5.mnm.userms.DTO;

import fr.polytech.ig5.mnm.userms.models.SectorType;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class CompanyUpdateDTO {

    @NotEmpty(message = "a company must have a name")
    private String name;

    @Email
    @NotEmpty(message = "a company must have an email")
    private String email;

    @NotEmpty(message = "a company must have a password")
    private String password;

    @Min(1)
    @NotEmpty(message = "a company must have an employees number")
    private Integer employeesNumber;

    @NotEmpty(message = "a company must have a description")
    private String description;

    @NotEmpty(message = "a company must have a sector")
    private SectorType sector;

    private String pictureUrl;
}
