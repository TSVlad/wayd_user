package ru.tsvlad.wayd_user.repo;

import org.springframework.data.repository.CrudRepository;
import ru.tsvlad.wayd_user.entity.RoleEntity;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
}
