package ru.tsvlad.wayd_user.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.tsvlad.wayd_user.data.entity.UserEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class JwtPayload {
    private String username;
    @JsonFormat(pattern = "YYYY-MM-dd")
    private LocalDate expiredAt;

    public JwtPayload(UserEntity userEntity) {
        this.username = userEntity.getUsername();
    }
}
