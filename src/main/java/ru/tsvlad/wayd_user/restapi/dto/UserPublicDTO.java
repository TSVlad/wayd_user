package ru.tsvlad.wayd_user.restapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserPublicDTO {
    private long id;
    private String username;
    private List<RoleDTO> roles;
    private String contacts;
}
