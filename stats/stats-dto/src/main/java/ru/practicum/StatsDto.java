package ru.practicum;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class StatsDto {
    private String uri;

    private String app;

    private Long hits;
}
