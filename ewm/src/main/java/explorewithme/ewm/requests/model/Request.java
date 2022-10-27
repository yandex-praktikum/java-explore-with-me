package explorewithme.ewm.requests.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import explorewithme.ewm.requests.Status;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests", schema = "public")
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Request {

    @Id
    @Column(name = "request_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "requestor_id")
    private long requester;

    @Column (name = "event_id")
    private long event;

    @Column(nullable = false, name = "created_at")
    @JsonSerialize
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    public Request(long requester, long event) {
        this.requester = requester;
        this.event = event;
        this.created = LocalDateTime.now();
        this.status = Status.PENDING;
    }
}
