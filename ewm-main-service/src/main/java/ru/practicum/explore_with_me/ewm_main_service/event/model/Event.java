package ru.practicum.explore_with_me.ewm_main_service.event.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.WhereJoinTable;
import ru.practicum.explore_with_me.ewm_main_service.category.model.Category;
import ru.practicum.explore_with_me.ewm_main_service.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 120, nullable = false)
    String title;

    @Column(length = 2000, nullable = false)
    String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    Category category;

    @Column(nullable = false)
    Boolean paid;

    @Column(name = "event_date")
    LocalDateTime eventDate;

    @Column(name = "published_on")
    LocalDateTime publishedOn;

    @OneToOne
    @JoinColumn(name = "initiator_id", referencedColumnName = "id", nullable = false)
    User initiator;

    @Column(length = 7000)
    String description;

    @Column(name = "participant_limit")
    Integer participantLimit = 0;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "state_enum")
    EventStateEnum state = EventStateEnum.PENDING;

    @Column(name = "created_on")
    LocalDateTime createdOn = LocalDateTime.now();

    @Column(nullable = false)
    Double latitude;

    @Column(nullable = false)
    Double longitude;

    @Column(name = "request_moderation")
    Boolean requestModeration = true;

    @WhereJoinTable(clause = "status_enum='0'")
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "requests",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "requester_id"))
    List<User> participants = new ArrayList<>();

    @Column(nullable = false)
    Long views = 0L;

    public boolean isAvailable() {
        return participantLimit == 0 || participantLimit > participants.size();
    }

    public Event(String annotation, Category category, String description, LocalDateTime eventDate, Location location,
                 Boolean paid, Integer participantLimit, Boolean requestModeration, String title) {
        this.annotation = annotation;
        this.category = category;
        this.description = description;
        this.eventDate = eventDate;
        this.latitude = location.getLat();
        this.longitude = location.getLon();
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.requestModeration = requestModeration;
        this.title = title;
    }

    public void update(Event event) {
        if (event.title != null) this.title = event.title;
        if (event.annotation != null) this.annotation = event.annotation;
        if (event.category != null) this.category = event.category;
        if (event.paid != null) this.paid = event.paid;
        if (event.eventDate != null) this.eventDate = event.eventDate;
        if (event.publishedOn != null) this.publishedOn = event.publishedOn;
        if (event.initiator != null) this.initiator = event.initiator;
        if (event.description != null) this.description = event.description;
        if (event.participantLimit != null) this.participantLimit = event.participantLimit;
        if (event.state != null) this.state = event.state;
        if (event.createdOn != null) this.createdOn = event.createdOn;
        if (event.latitude != null) this.latitude = event.latitude;
        if (event.longitude != null) this.longitude = event.longitude;
        if (event.requestModeration != null) this.requestModeration = event.requestModeration;
        if (event.views != null) this.views = event.views;
    }
}
