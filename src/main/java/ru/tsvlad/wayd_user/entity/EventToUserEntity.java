package ru.tsvlad.wayd_user.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "event_to_participant")
@Getter
@Setter
public class EventToUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "event_id")
    private String eventId;

    @Column(name = "user_id")
    private long userId;
}
