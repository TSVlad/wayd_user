package ru.tsvlad.wayd_user.messaging.producer.msg;

import lombok.*;
import ru.tsvlad.wayd_user.messaging.AbstractMessage;
import ru.tsvlad.wayd_user.messaging.dto.ConfirmationCodeDTO;
import ru.tsvlad.wayd_user.messaging.dto.EmailCredentialsDTO;
import ru.tsvlad.wayd_user.restapi.dto.UserWithoutPasswordDTO;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMessage extends AbstractMessage {
    private UserMessageType type;
    private UserWithoutPasswordDTO userDTO;
    private ConfirmationCodeDTO confirmationCodeDTO;
    private EmailCredentialsDTO emailCredentialsDTO;
}
