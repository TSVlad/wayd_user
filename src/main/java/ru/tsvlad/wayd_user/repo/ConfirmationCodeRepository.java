package ru.tsvlad.wayd_user.repo;

import org.springframework.data.repository.CrudRepository;
import ru.tsvlad.wayd_user.entity.ConfirmationCodeEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface ConfirmationCodeRepository extends CrudRepository<ConfirmationCodeEntity, Long> {
    List<ConfirmationCodeEntity> findAllByEmailAndExpirationAfter(String email, LocalDateTime expiration);

}
