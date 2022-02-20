package ru.tsvlad.wayd_user.restapi.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRegisterDTO {
    private String username;
    private String password;
    private String name;
    private String surname;
    private String description;
    private String contacts;
    private String email;
    private LocalDate dateOfBirth;
    private String avatar;
}
