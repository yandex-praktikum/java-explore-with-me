package ru.practicum.server.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "hits", schema = "public")
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "app", nullable = false)
    private String app;
    @Column(name = "uri", nullable = false)
    private String uri;
    @Column(name = "ip", nullable = false)
    private String ip;
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;
}