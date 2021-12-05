package ru.tsvlad.wayd_user.messaging.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailCredentialsDTO {
    private long userId;
    private String email;
    private String username;
    private String password;
}
