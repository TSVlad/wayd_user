package ru.tsvlad.wayd_user.restapi.dto;

import lombok.Data;
import ru.tsvlad.wayd_user.entity.RoleEntity;
import ru.tsvlad.wayd_user.enums.UserStatus;
import ru.tsvlad.wayd_user.enums.Validity;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDTO {
    private long id;
    private String username;
    private List<RoleDTO> roles;
    private String name;
    private String surname;
    private String description;
    private String contacts;
    private String email;
    private Validity validityBadWords;
    private UserStatus status;
    private LocalDate dateOfBirth;
}
