package ru.tsvlad.wayd_user.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.tsvlad.wayd_user.enums.Role;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString
public class RoleEntity {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private Role name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleEntity that = (RoleEntity) o;
        return id == that.id && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
