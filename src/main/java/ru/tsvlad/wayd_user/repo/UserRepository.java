package ru.tsvlad.wayd_user.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tsvlad.wayd_user.data.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
