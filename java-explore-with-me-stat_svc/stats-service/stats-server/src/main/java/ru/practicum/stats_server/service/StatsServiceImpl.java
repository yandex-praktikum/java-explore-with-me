package ru.practicum.stats_server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats_common.CommonUtils;
import ru.practicum.stats_common.model.EndpointHit;
import ru.practicum.stats_common.model.ViewStats;
import ru.practicum.stats_server.mapper.StatsMapper;
import ru.practicum.stats_server.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;
    private final StatsMapper statsMapper;

    @Override
    @Transactional
    public void addHit(EndpointHit endpointHit) {
        log.info("Регистрация обращения к {}", endpointHit);

        statsRepository.save(statsMapper.toStats(endpointHit,
                LocalDateTime.parse(endpointHit.getTimestamp(), CommonUtils.DT_FORMATTER)));
    }

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        log.info("Вывод списка обращений по параметрам start = {}, end = {}, uris = {}, unique = {}",
                start, end, uris, unique);

        if (uris == null || uris.isEmpty()) {
            if (unique) {
                return statsRepository.getAllStatsDistinctIp(start, end);
            } else {
                return statsRepository.getAllStats(start, end);
            }
        } else {
            if (unique) {
                return statsRepository.getStatsByUrisDistinctIp(start, end, uris);
            } else {
                return statsRepository.getStatsByUris(start, end, uris);
            }
        }
    }
}
