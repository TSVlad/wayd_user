package ru.tsvlad.wayd_user.messaging.producer;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.tsvlad.wayd_user.entity.ConfirmationCodeEntity;
import ru.tsvlad.wayd_user.messaging.dto.ConfirmationCodeDTO;
import ru.tsvlad.wayd_user.messaging.producer.msg.UserMessage;
import ru.tsvlad.wayd_user.messaging.producer.msg.UserMessageType;
import ru.tsvlad.wayd_user.restapi.dto.UserDTO;
import ru.tsvlad.wayd_user.restapi.dto.UserPublicDTO;
import ru.tsvlad.wayd_user.restapi.dto.UserWithoutPasswordDTO;
import ru.tsvlad.wayd_user.utils.MappingUtils;

@Service
@AllArgsConstructor
public class UserServiceProducer {
    private final KafkaTemplate<Long, UserMessage> userMessageKafkaTemplate;

    public void registerAccount(UserWithoutPasswordDTO userDTO) {
        send(UserMessage.builder()
                .type(UserMessageType.CREATED)
                .userDTO(userDTO)
                .build());
    }

    public void updateAccount(UserWithoutPasswordDTO userPublicDTO) {
        send(UserMessage.builder()
                .type(UserMessageType.UPDATED)
                .userDTO(userPublicDTO)
                .build());
    }

    public void createConfirmationCode(ConfirmationCodeEntity codeEntity) {
        send(UserMessage.builder()
                .type(UserMessageType.CONFIRMATION_CODE_GENERATED)
                .confirmationCodeDTO(MappingUtils.map(codeEntity, ConfirmationCodeDTO.class))
                .build());
    }

    private void send(UserMessage message) {
        userMessageKafkaTemplate.send("user-topic", message);
    }
}
