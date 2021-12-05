package ru.tsvlad.wayd_user.restapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserWithoutPasswordDTO {
    private long id;
    private String username;
    private List<RoleDTO> roles;
    private String name;
    private String surname;
    private String contacts;
    private String email;
}
