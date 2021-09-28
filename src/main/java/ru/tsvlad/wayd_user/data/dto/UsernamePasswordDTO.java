package ru.tsvlad.wayd_user.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsernamePasswordDTO {
    private String username;
    private String password;
}
