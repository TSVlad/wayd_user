package ru.tsvlad.wayd_user.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tsvlad.wayd_user.enums.Role;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String id;
    private String username;
    private List<Role> roles;
    private String name;
    private String surname;
    private String description;
    private String contacts;
    private String email;
}
