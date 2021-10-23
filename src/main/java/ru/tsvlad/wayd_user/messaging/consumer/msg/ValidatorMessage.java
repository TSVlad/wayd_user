package ru.tsvlad.wayd_user.messaging.consumer.msg;

import lombok.*;
import ru.tsvlad.wayd_user.messaging.AbstractMessage;
import ru.tsvlad.wayd_user.messaging.consumer.msg.type.ValidatorMessageType;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidatorMessage extends AbstractMessage {
    private ValidatorMessageType type;
    private String eventId;
    private long userId;
    private boolean isValid;
}
