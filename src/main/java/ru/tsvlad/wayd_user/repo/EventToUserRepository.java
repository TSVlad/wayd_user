package ru.tsvlad.wayd_user.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.tsvlad.wayd_user.entity.EventToUserEntity;

@Repository
public interface EventToUserRepository extends PagingAndSortingRepository<EventToUserEntity, Long> {

    Page<EventToUserEntity> findAllByUserId(Pageable pageable, long userId);
}
