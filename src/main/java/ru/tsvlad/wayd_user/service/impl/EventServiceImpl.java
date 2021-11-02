package ru.tsvlad.wayd_user.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.tsvlad.wayd_user.entity.EventToUserEntity;
import ru.tsvlad.wayd_user.repo.EventToUserRepository;
import ru.tsvlad.wayd_user.service.EventService;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    private EventToUserRepository eventToUserRepository;

    @Override
    public Page<String> getEventIdsForUser(Pageable pageable, long id) {
        return eventToUserRepository.findAllByUserId(pageable, id).map(EventToUserEntity::getEventId);
    }
}
