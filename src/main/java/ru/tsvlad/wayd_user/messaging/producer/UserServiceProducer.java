package ru.tsvlad.wayd_user.messaging.producer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.tsvlad.wayd_user.messaging.dto.ConfirmationCodeDTO;
import ru.tsvlad.wayd_user.messaging.dto.EmailCredentialsDTO;
import ru.tsvlad.wayd_user.messaging.producer.msg.UserMessage;
import ru.tsvlad.wayd_user.messaging.producer.msg.UserMessageType;
import ru.tsvlad.wayd_user.messaging.dto.UserKafkaDTO;
import ru.tsvlad.wayd_user.utils.MappingUtils;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceProducer {
    private final KafkaTemplate<Long, UserMessage> userMessageKafkaTemplate;

    public void registerAccount(UserKafkaDTO userDTO) {
        log.info("MOCK"); //TODO: implement
        /*send(UserMessage.builder()
                .type(UserMessageType.CREATED)
                .userDTO(userDTO)
                .build());*/
    }

    public void updateAccount(UserKafkaDTO userPublicDTO) {
        send(UserMessage.builder()
                .type(UserMessageType.UPDATED)
                .userDTO(userPublicDTO)
                .build());
    }


    public void organizationRegistered(EmailCredentialsDTO emailCredentialsDTO) {
        send(UserMessage.builder()
                .type(UserMessageType.ORGANIZATION_REGISTERED)
                .emailCredentialsDTO(emailCredentialsDTO)
                .build());
    }

    private void send(UserMessage message) {
        userMessageKafkaTemplate.send("user-topic", message);
    }
}
