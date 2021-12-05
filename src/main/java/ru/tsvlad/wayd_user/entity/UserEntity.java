package ru.tsvlad.wayd_user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tsvlad.wayd_user.enums.Role;
import ru.tsvlad.wayd_user.enums.UserStatus;
import ru.tsvlad.wayd_user.enums.Validity;
import ru.tsvlad.wayd_user.restapi.controller.advise.exceptions.InvalidPasswordException;
import ru.tsvlad.wayd_user.restapi.dto.OrganizationForRegisterDTO;
import ru.tsvlad.wayd_user.restapi.dto.UserForRegisterDTO;
import ru.tsvlad.wayd_user.restapi.dto.UserForUpdateDTO;
import ru.tsvlad.wayd_user.service.RoleService;
import ru.tsvlad.wayd_user.utils.MappingUtils;
import ru.tsvlad.wayd_user.utils.PasswordUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

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
    private Set<RoleEntity> roles;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "description")
    private String description;

    @Column(name = "contacts")
    private String contacts;

    @Column(name = "email")
    private String email;

    @Column(name = "valid_bad_words")
    @Enumerated(EnumType.STRING)
    private Validity validityBadWords;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

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

        Set<RoleEntity> roles = new HashSet<>();
        roles.add(roleService.getRoleEntityByName(Role.ROLE_USER));
        roles.add(roleService.getRoleEntityByName(Role.ROLE_PERSON));
        result.setRoles(roles);
        result.setStatus(UserStatus.NOT_APPROVED_EMAIL);
        result.setPassword(PasswordUtils.encodePassword(result.password));
        result.setValidityBadWords(Validity.NOT_VALIDATED);

        return result;
    }

    public static UserEntity registerOrganization(OrganizationForRegisterDTO organizationForRegisterDTO, String password, RoleService roleService) {
        UserEntity result = new UserEntity();
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(roleService.getRoleEntityByName(Role.ROLE_USER));
        roles.add(roleService.getRoleEntityByName(Role.ROLE_ORGANIZATION));
        result.setRoles(roles);

        result.setPassword(PasswordUtils.encodePassword(password));
        result.setEmail(organizationForRegisterDTO.getEmail());
        result.setUsername(organizationForRegisterDTO.getUsername());

        result.setValidityBadWords(Validity.NOT_VALIDATED);
        result.setStatus(UserStatus.ACTIVE);

        return result;
    }

    public void updateUser(UserForUpdateDTO user) {
        this.name = user.getName();
        this.surname = user.getSurname();
        this.contacts = user.getContacts();
        this.username = user.getUsername();
        this.description = user.getDescription();
        this.status = UserStatus.ON_VALIDATION;
        this.validityBadWords = Validity.NOT_VALIDATED;
    }

    public void confirmEmail() {
        this.status = UserStatus.ON_VALIDATION;
    }

    public void updateValidBadWords(Validity validity) {
        this.validityBadWords = validity;
        switch (validity) {
            case NOT_VALID:
                this.status = UserStatus.INVALID;
                break;
            case VALID:
                this.status = UserStatus.ACTIVE;
        }
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (!PasswordUtils.isRealPassword(oldPassword, this.password)) {
            throw new InvalidPasswordException();
        }
        this.setPassword(PasswordUtils.encodePassword(newPassword));
    }

    public void ban() {
        this.status = UserStatus.BANNED;
    }

    public void unban() {
        this.status = UserStatus.ACTIVE;
    }
}
