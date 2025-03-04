package ru.practicum.stats;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Stat {
    private String uri;

    private String app;

    private Long hits;
}

