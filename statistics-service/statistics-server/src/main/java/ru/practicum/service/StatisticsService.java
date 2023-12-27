package ru.practicum.service;

import ru.practicum.dto.InputEventDto;
import ru.practicum.dto.OutputStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticsService {
    InputEventDto saveEventStats(InputEventDto dto);

    List<OutputStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
