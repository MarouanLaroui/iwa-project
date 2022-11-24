package fr.polytech.ig5.mnm.userms.DTO;

import fr.polytech.ig5.mnm.userms.models.SectorType;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;

@Data
public class CompanyUpdateDTO {

    private String name;

    @Email
    private String email;

    private String password;

    @Min(1)
    private Integer employeesNumber;

    private String description;

    private SectorType sector;

    private String pictureUrl;
}
