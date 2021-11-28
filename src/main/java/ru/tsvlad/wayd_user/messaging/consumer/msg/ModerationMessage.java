package ru.tsvlad.wayd_user.messaging.consumer.msg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.tsvlad.wayd_user.messaging.AbstractMessage;
import ru.tsvlad.wayd_user.messaging.consumer.msg.type.ModerationMessageType;
import ru.tsvlad.wayd_user.messaging.dto.BanDTO;


@EqualsAndHashCode(callSuper = true)
@Data
public class ModerationMessage extends AbstractMessage {
    private ModerationMessageType type;
    private BanDTO banDTO;
}
