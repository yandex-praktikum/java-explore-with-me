package ru.practicum.service;

import ru.practicum.HitDto;
import ru.practicum.StatsDto;

import java.util.List;

public interface StatsService {
    public HitDto addHit(HitDto hitDto);

    public List<StatsDto> getStats(String start,
                                   String end,
                                   List<String> uris,
                                   Boolean unique);
}