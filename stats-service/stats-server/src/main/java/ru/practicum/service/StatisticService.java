package ru.practicum.service;

import ru.practicum.StatisticEventDto;
import ru.practicum.StatisticViewDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticService {
    StatisticEventDto addEvent(StatisticEventDto eventDto);
    List<StatisticViewDto> getEvents(LocalDateTime startTime, LocalDateTime endTime, List<String> uris, boolean unique);
}
