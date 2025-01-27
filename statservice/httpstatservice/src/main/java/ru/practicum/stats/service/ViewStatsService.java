package ru.practicum.stats.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.endpointhit.repository.EndpointHitStorageJpa;
import ru.practicum.stats.model.ViewStats;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ViewStatsService {
    private final EndpointHitStorageJpa endpointHitStorageJpa;

    @Autowired
    ViewStatsService(EndpointHitStorageJpa endpointHitStorageJpa) {
        this.endpointHitStorageJpa = endpointHitStorageJpa;
    }

    public List<ViewStats> getViewStats(LocalDateTime start, LocalDateTime end, List<String> uris,  Boolean unique) {
        if (uris == null && unique == null) {
            return endpointHitStorageJpa.getStatsBetweenStartAndEnd(start, end);
        } else if (uris != null && !uris.isEmpty() && unique == null) {
            return endpointHitStorageJpa.getStatsWithUris(start, end, uris);
        } else if (unique && uris == null) {
            return endpointHitStorageJpa.getStatsWithUniqueIp(start, end);
        } else if (unique && uris != null) {
            return endpointHitStorageJpa.getStatsWithUrisAndUniqueIp(start, end, uris);
        }
        return new ArrayList<>();
    }
}
