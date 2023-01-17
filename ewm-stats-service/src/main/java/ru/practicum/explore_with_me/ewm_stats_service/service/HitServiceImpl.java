package ru.practicum.explore_with_me.ewm_stats_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.ewm_stats_service.dto.EndpointHitDto;
import ru.practicum.explore_with_me.ewm_stats_service.dto.ViewStatsDto;
import ru.practicum.explore_with_me.ewm_stats_service.mapper.HitMapper;
import ru.practicum.explore_with_me.ewm_stats_service.model.Hit;
import ru.practicum.explore_with_me.ewm_stats_service.repository.HitCustomRepository;
import ru.practicum.explore_with_me.ewm_stats_service.repository.HitRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HitServiceImpl implements HitService {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final HitRepository hitRepository;
    private final HitCustomRepository hitCustomRepository;

    private final HitMapper hitMapper;

    @Transactional
    @Override
    public EndpointHitDto saveHit(EndpointHitDto endpointHit) {
        Hit savedHit = hitMapper.toHit(endpointHit);
        return hitMapper.toDto(hitRepository.save(savedHit));
    }

    @Override
    public List<ViewStatsDto> getStats(String start, String end, String[] uris, Boolean unique) {
        List<Hit> sqlResult = hitCustomRepository.findAllByUri(
                LocalDateTime.parse(start, DATE_TIME_FORMATTER),
                LocalDateTime.parse(end, DATE_TIME_FORMATTER),
                uris, unique);

        Map<String, Map<String, Long>> groups = sqlResult.stream()
                .collect(Collectors.groupingBy(Hit::getApp,
                        Collectors.groupingBy(Hit::getUri, Collectors.counting())));

        List<ViewStatsDto> result = new ArrayList<>();
        for (String appName : groups.keySet()) {
            Map<String, Long> appUriCollection = groups.get(appName);
            for (String uriPath : appUriCollection.keySet()) {
                result.add(new ViewStatsDto(appName, uriPath, appUriCollection.get(uriPath)));
            }
        }
        return result;
    }
}
