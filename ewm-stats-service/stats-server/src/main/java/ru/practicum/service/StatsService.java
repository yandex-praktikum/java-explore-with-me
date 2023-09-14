package ru.practicum.service;

import ru.practicum.EndpointHitDto;
import ru.practicum.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    void saveHit(EndpointHitDto hitDto);

    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris);

    List<ViewStats> getUniqueStats(LocalDateTime start, LocalDateTime end, List<String> uris);
}
