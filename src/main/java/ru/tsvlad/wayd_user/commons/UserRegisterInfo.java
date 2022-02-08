package ru.tsvlad.wayd_user.commons;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRegisterInfo {
    private String username;
    private String password;
    private String name;
    private String surname;
    private String description;
    private String contacts;
    private String email;
    private LocalDate dateOfBirth;
}
