package ru.tsvlad.wayd_user.restapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserForRegisterDTO {
    private String username;
    private String password;
    private String contacts;
    private String email;

    private boolean isOrganization;
}
