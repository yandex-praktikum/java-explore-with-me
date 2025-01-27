package ru.practicum.dto;

import lombok.Data;

@Data
public class ViewStatsDto {
    private String app;
    private String uri;
    private Long hits;
}
