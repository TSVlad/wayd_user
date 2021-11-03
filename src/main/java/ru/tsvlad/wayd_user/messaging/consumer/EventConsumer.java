package ru.tsvlad.wayd_user.messaging.consumer;

import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.tsvlad.wayd_user.entity.EventToUserEntity;
import ru.tsvlad.wayd_user.messaging.consumer.msg.EventMessage;
import ru.tsvlad.wayd_user.service.EventService;

@Component
@AllArgsConstructor
public class EventConsumer {
    private EventService eventService;

    @KafkaListener(topics = {"event-to-user"}, containerFactory = "singleFactory")
    public void consume(EventMessage eventMessage) {
        switch (eventMessage.getType()) {
            case NEW_PARTICIPANT:
                newParticipant(eventMessage);
                break;
        }
    }

    private void newParticipant(EventMessage eventMessage) {
        EventToUserEntity eventToUserEntity = new EventToUserEntity();
        eventToUserEntity.setEventId(eventMessage.getEventDTO().getId());
        eventToUserEntity.setUserId(eventMessage.getUserId());
        eventService.saveEventToParticipants(eventToUserEntity);
    }
}
