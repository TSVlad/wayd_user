package ru.tsvlad.wayd_user.entity;

import lombok.*;
import ru.tsvlad.wayd_user.enums.Role;
import ru.tsvlad.wayd_user.enums.UserStatus;
import ru.tsvlad.wayd_user.restapi.controller.advise.exceptions.ForbiddenException;
import ru.tsvlad.wayd_user.restapi.dto.UserDTO;
import ru.tsvlad.wayd_user.restapi.dto.UserForRegisterDTO;
import ru.tsvlad.wayd_user.restapi.dto.UserForUpdateDTO;
import ru.tsvlad.wayd_user.service.RoleService;
import ru.tsvlad.wayd_user.utils.MappingUtils;
import ru.tsvlad.wayd_user.utils.PasswordEncodeUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private List<RoleEntity> roles;

    @Column(name = "contacts")
    private String contacts;

    @Column(name = "email")
    private String email;

    @Column(name = "valid_bad_words")
    private boolean isValidBadWords;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id && username.equals(that.username) && password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }

    public static UserEntity registerUser(UserForRegisterDTO userDTO, RoleService roleService) {
        UserEntity result = MappingUtils.map(userDTO, UserEntity.class);

        List<RoleEntity> roles = new ArrayList<>();
        roles.add(roleService.getRoleEntityByName(Role.ROLE_USER));
        roles.add(userDTO.isOrganization() ?
                roleService.getRoleEntityByName(Role.ROLE_ORGANIZATION)
                : roleService.getRoleEntityByName(Role.ROLE_PERSON));
        result.setRoles(roles);
        result.setStatus(UserStatus.NOT_APPROVED_EMAIL);
        result.setPassword(PasswordEncodeUtils.encodePassword(result.password));
        return result;
    }

    public void updateUser(UserForUpdateDTO user) {
        this.contacts = user.getContacts();
        this.username = user.getUsername();
        this.setStatus(UserStatus.ON_VALIDATION);
    }

    public void confirmEmail() {
        this.status = UserStatus.ON_VALIDATION;
    }
}
