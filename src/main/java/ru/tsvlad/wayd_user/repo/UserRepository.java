package ru.tsvlad.wayd_user.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.tsvlad.wayd_user.entity.UserEntity;
import ru.tsvlad.wayd_user.enums.UserStatus;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);
    Page<UserEntity> findAllByUsernameLikeAndStatus(Pageable pageable, String str, UserStatus userStatus);
}
