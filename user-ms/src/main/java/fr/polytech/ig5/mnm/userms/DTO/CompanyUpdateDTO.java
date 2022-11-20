package fr.polytech.ig5.mnm.userms.DTO;

import fr.polytech.ig5.mnm.userms.models.SectorType;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CompanyUpdateDTO {

    @NotEmpty(message = "a company must have a name")
    private String name;

    @NotEmpty(message = "a company must have an email")
    @Email
    private String email;

    @NotNull(message = "a company must have an employees number (at least 1)")
    @Min(1)
    private Integer employeesNumber;

    @NotEmpty(message = "a company must have a description")
    private String description;

    @NotNull(message = "a company must have a sector")
    private SectorType sector;

    private String pictureUrl;
}
