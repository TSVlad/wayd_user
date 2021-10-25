package ru.tsvlad.wayd_user.messaging.producer.msg;

import lombok.*;
import ru.tsvlad.wayd_user.messaging.AbstractMessage;
import ru.tsvlad.wayd_user.messaging.dto.ConfirmationCodeDTO;
import ru.tsvlad.wayd_user.restapi.dto.UserPublicDTO;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMessage extends AbstractMessage {
    private UserMessageType type;
    private UserPublicDTO userDTO;
    private ConfirmationCodeDTO confirmationCodeDTO;
}
