package ru.practicum.explore_with_me.ewm_stats_service.repository;

import ru.practicum.explore_with_me.ewm_stats_service.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

public interface HitCustomRepository {

    List<Hit> findAllByUri(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique);
}
