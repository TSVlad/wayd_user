package ru.tsvlad.wayd_user.commons;

import lombok.Data;

@Data
public class UserUpdateInfo {
    private String id;
    private String name;
    private String surname;
    private String contacts;
    private String description;
    private String avatar;
}
