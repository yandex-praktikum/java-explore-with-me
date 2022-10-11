package ru.practicum.stats.repository;

import ru.practicum.stats.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsParamRepository {
    List<ViewStats> findStatsWithParam(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
