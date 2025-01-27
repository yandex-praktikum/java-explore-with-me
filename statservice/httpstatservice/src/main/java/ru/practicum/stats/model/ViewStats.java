package ru.practicum.stats.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ViewStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "app")
    private String app;

    @Column(name = "uri")
    private String uri;

    @Column(name = "hits")
    private Long hits;

    public ViewStats(String app, String uri, Long hits) {
        this.app = app;
        this.uri = uri;
        this.hits = hits;
    }

    public ViewStats() {

    }
}
