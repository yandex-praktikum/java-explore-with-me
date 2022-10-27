package explorewithme.ewm.events.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import explorewithme.ewm.events.State;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "events", schema = "public")
@ToString
@Data
@NoArgsConstructor

public class Event {

    @Id
    @Column (name = " event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String title;
    @Column
    private String annotation;
    @Column
    private String description;
    @Column (name = "initiator_id" )
    private long initiator;
    @Column (name = "category_id")
    @NotNull
    private long category;
    @Column (name = "created_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
    @Column (name = "event_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @Column (name = "published_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime published;
    @Column
    private boolean paid;
    @Column (name = "request_moderation")
    private boolean requestModeration;
    @Column (name = "participant_limit")
    int participantLimit;
    @Column
    double lat;
    @Column
    double lon;
    @Column
    @Enumerated(EnumType.STRING)
    private State state;

    @Column
    private long views;

    public Event(String title, String annotation, String description, long category, long initiator, LocalDateTime eventDate) {
        this.title = title;
        this.annotation = annotation;
        this.description = description;
        this.category = category;
        this.created = LocalDateTime.now();
        this.eventDate = eventDate;
        this.initiator = initiator;
        this.paid = false;
        this.participantLimit = 0;
        this.requestModeration = true;
        this.state = State.PENDING;
    }
}
