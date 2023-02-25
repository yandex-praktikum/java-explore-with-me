package ru.practicum.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ViewStatsDto {
    private String app;
    private String uri;
    private Long hits;
}