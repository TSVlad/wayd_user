package ru.tsvlad.wayd_user.restapi.dto;

import lombok.Data;
import ru.tsvlad.wayd_user.entity.RoleEntity;

import java.util.List;

@Data
public class UserDTO {
    private long id;
    private String username;
    private String password;
    private List<RoleDTO> roles;
    private String contacts;
    private String email;
}
