package ru.practicum.ewm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import ru.practicum.ewm.model.enums.EventStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "annotation")
    private String annotation;
    @Column(name = "description")
    private String description;
    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;
    @ManyToOne(cascade = CascadeType.ALL)
    private User initiator;
    @Column(name = "participant_limit")
    private Integer participantLimit;
    @Column(name = "confirmed_requests")
    private int confirmedRequests;
    @Column(name = "lat")
    private Double lat;
    @Column(name = "lon")
    private Double lon;
    @Column(name = "paid")
    private Boolean paid;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Type(type = "ru.practicum.ewm.model.enums.EnumTypePostgeSql")
    private EventStatus eventStatus;
    @Column(name = "event_date")
    private LocalDateTime eventDate;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Column(name = "created")
    private LocalDateTime created;
}
