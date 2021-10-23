package ru.tsvlad.wayd_user.entity;

import lombok.*;
import ru.tsvlad.wayd_user.restapi.dto.UserDTO;
import ru.tsvlad.wayd_user.restapi.dto.UserForUpdateDTO;
import ru.tsvlad.wayd_user.utils.MappingUtils;
import ru.tsvlad.wayd_user.utils.PasswordEncodeUtils;

import javax.persistence.*;
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

    public static UserEntity registerUser(UserDTO userDTO) {
        UserEntity result = MappingUtils.map(userDTO, UserEntity.class);
        result.setPassword(PasswordEncodeUtils.encodePassword(result.password));
        return result;
    }

    public void updateUser(UserForUpdateDTO user) {
        this.contacts = user.getContacts();
        this.username = user.getUsername();
    }
}
