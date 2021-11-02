package ru.tsvlad.wayd_user.restapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tsvlad.wayd_user.entity.RoleEntity;
import ru.tsvlad.wayd_user.entity.UserEntity;
import ru.tsvlad.wayd_user.enums.Role;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class JwtPayload {
    private long id;
    private String username;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiredAt;
    private List<Role> roles;

    public JwtPayload(UserEntity userEntity) {
        this.username = userEntity.getUsername();
        this.roles = userEntity.getRoles().stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toList());
        this.id = userEntity.getId();
        this.dateOfBirth = userEntity.getDateOfBirth();
    }
}
