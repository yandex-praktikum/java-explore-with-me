package com.explore.exploreWithMe.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



import java.time.LocalDateTime;

@Entity
@Table(name = "hit")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hit")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hit_uri")
    private App app;

    @Column(name = "ip_user")
    private String ip;

    @Column(name = "TIME_HIT")
    private LocalDateTime timestamp;
}
