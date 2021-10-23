package ru.tsvlad.wayd_user.messaging.producer;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.tsvlad.wayd_user.messaging.producer.msg.UserMessage;
import ru.tsvlad.wayd_user.messaging.producer.msg.UserMessageType;
import ru.tsvlad.wayd_user.restapi.dto.UserPublicDTO;

@Service
@AllArgsConstructor
public class UserServiceProducer {
    private final KafkaTemplate<Long, UserMessage> userMessageKafkaTemplate;

    public void registerAccount(UserPublicDTO userDTO) {
        send(UserMessage.builder()
                .type(UserMessageType.CREATED)
                .userDTO(userDTO)
                .build());
    }

    public void updateAccount(UserPublicDTO userPublicDTO) {
        send(UserMessage.builder()
                .type(UserMessageType.UPDATED)
                .userDTO(userPublicDTO)
                .build());
    }

    private void send(UserMessage message) {
        userMessageKafkaTemplate.send("user-topic", message);
    }
}
