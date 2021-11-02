package ru.tsvlad.wayd_user.restapi.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserForRegisterDTO {
    private String username;
    private String password;
    private String description;
    private String contacts;
    private String email;
    private LocalDate dateOfBirth;

    private boolean isOrganization;
}
