package ru.tsvlad.wayd_user.restapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.tsvlad.wayd_user.entity.RoleEntity;
import ru.tsvlad.wayd_user.entity.UserEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class JwtPayload {
    private String username;
    @JsonFormat(pattern = "YYYY-MM-dd")
    private LocalDate expiredAt;
    private List<String> roles;

    public JwtPayload(UserEntity userEntity) {
        this.username = userEntity.getUsername();
        this.roles = userEntity.getRoles().stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toList());
    }
}
