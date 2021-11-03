package ru.tsvlad.wayd_user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.tsvlad.wayd_user.entity.EventToUserEntity;

import java.util.List;

public interface EventService {
    Page<String> getEventIdsForUser(Pageable pageable, long id);
    void saveEventToParticipants(EventToUserEntity eventToUserEntity);
}
