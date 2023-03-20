package ru.practicum.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStats;
import ru.practicum.mapper.EndPointHitMapper;
import ru.practicum.repository.StatsRepository;
import ru.practicum.stats.stats.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    private final EndPointHitMapper mapper;

    public StatsServiceImpl(StatsRepository statsRepository, EndPointHitMapper mapper) {
        this.statsRepository = statsRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public EndpointHitDto postHit(EndpointHitDto endpointHitDto) {
        return mapper.toEndpointHitDto(statsRepository.save(mapper.toEndpointHit(endpointHitDto)));
    }

    @Override
    public List<ViewStats> getViewStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        List<ViewStats> viewStats = new ArrayList<>();
        List<EndpointHit> hitsList;
        for (String uri : uris) {
            if (unique) {
                hitsList = statsRepository.findDistinctByUriInAndTimestampBetween(List.of(uri), start, end);
            } else {
                hitsList = statsRepository.findByUriInAndTimestampBetween(List.of(uri), start, end);
            }
            viewStats.add(new ViewStats("ewm-main-service", uri, hitsList.size()));
            viewStats.sort(Comparator.comparing(ViewStats::getHits).reversed());
        }
        return viewStats;
    }
}
