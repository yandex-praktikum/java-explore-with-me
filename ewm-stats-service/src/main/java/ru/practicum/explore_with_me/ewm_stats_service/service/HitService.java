package ru.practicum.explore_with_me.ewm_stats_service.service;

import ru.practicum.explore_with_me.ewm_stats_service.dto.ViewStatsDto;
import ru.practicum.explore_with_me.ewm_stats_service.dto.EndpointHitDto;


import java.util.List;

public interface HitService {
    EndpointHitDto saveHit(EndpointHitDto endpointHit);

    List<ViewStatsDto> getStats(String start, String end, String[] uris, Boolean uniq);
}
