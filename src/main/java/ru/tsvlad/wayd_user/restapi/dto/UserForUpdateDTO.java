package ru.tsvlad.wayd_user.restapi.dto;

import lombok.Data;

@Data
public class UserForUpdateDTO {
    private long id;
    private String username;
    private String name;
    private String surname;
    private String contacts;
    private String description;
}
