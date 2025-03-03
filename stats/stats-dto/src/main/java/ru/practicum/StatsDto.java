package ru.practicum;

import lombok.Data;

@Data
public class StatsDto {
    private String uri;

    private String app;

    private Long hits;
}
