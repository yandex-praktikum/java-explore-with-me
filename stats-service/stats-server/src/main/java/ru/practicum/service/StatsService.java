package ru.practicum.service;


import ru.practicum.EndpointHitDto;
import ru.practicum.ViewStatsDto;


import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {

    void createHit(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique);
}