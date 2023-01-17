package ru.practicum.explore_with_me.ewm_stats_service.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hits")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 50)
    String app;

    @Column(length = 256)
    String uri;

    @Column(length = 50)
    String ip;

    @Column(nullable = false)
    LocalDateTime timestamp;

}
