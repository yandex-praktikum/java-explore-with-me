package ru.practicum.ewm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import ru.practicum.ewm.model.enums.RequestStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "participation_requests")
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User requestor;
    @ManyToOne
    private Event event;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Type(type = "ru.practicum.ewm.model.enums.EnumTypePostgeSql")
    private RequestStatus status;
    @Column(name = "created")
    private LocalDateTime created;
}
