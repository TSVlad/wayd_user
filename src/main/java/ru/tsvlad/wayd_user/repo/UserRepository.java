package ru.tsvlad.wayd_user.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tsvlad.wayd_user.entity.UserEntity;
import ru.tsvlad.wayd_user.enums.UserStatus;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);
}
