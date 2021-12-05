package ru.tsvlad.wayd_user.restapi.dto;

import lombok.Data;
import ru.tsvlad.wayd_user.entity.RoleEntity;

import java.util.List;

@Data
public class UserForOwnerDTO {
    private long id;
    private String username;
    private List<RoleEntity> roles;
    private String name;
    private String surname;
    private String description;
    private String contacts;
    private String email;
}
