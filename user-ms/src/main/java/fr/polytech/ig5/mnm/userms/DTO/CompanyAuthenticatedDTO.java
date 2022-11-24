package fr.polytech.ig5.mnm.userms.DTO;

import fr.polytech.ig5.mnm.userms.models.SectorType;
import lombok.Data;

import javax.validation.constraints.Email;
import java.util.UUID;

@Data
public class CompanyAuthenticatedDTO {

    private UUID id;

    private String name;

    @Email
    private String email;

    private String employeesNumber;

    private String description;

    private SectorType sector;

    private String pictureUrl;

    private String authorizationToken;

}
