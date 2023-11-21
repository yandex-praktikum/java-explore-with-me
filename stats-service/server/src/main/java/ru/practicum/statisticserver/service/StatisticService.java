package ru.practicum.statisticserver.service;

import ru.practicum.statdto.EndpointHitDto;
import ru.practicum.statdto.ViewStats;
import java.time.LocalDateTime;
import java.util.List;

public interface StatisticService {
    EndpointHitDto addStatisticData(EndpointHitDto endpointHit);

    List<ViewStats> getStatisticData(LocalDateTime start, LocalDateTime end, List<String> uris, boolean isUnique);
}
