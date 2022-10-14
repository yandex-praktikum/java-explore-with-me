package ru.practikum.explorewithme.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "participation", schema = "public")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime created;

    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id", updatable = false)
    private Event event;

    @ManyToOne(optional = false)
    @JoinColumn(name = "requester_id", updatable = false)
    private User requester;

    private ParticipationStatus status;
}
