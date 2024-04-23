package ru.practicum.ewm.stats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;
import ru.practicum.ewm.stats.mapper.HitMapper;
import ru.practicum.ewm.stats.model.Hit;
import ru.practicum.ewm.stats.repository.HitJpaRepository;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {

    private final HitJpaRepository hitJpaRepository;

    @Override
    public EndpointHit create(EndpointHit endpointHit) {
        Hit hit = HitMapper.toHit(endpointHit);
        hit = hitJpaRepository.save(hit);
        log.info("Create endpoint hit {}", hit);
        return HitMapper.toEndpointHit(hit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {

        int isSearchUrl = (uris == null || uris.isEmpty() ? 0 : 1);
        List<Object[]> list = unique ?
                hitJpaRepository.findStatisticsBetweenStartAndEndUniqueIp(start, end, isSearchUrl, uris) :
                hitJpaRepository.findStatisticsBetweenStartAndEnd(start, end, isSearchUrl, uris);

        List<ViewStats> stats = convertViewStatesFromObject(list);
        log.info("stats --> {}", stats);
        return stats;
    }

    private List<ViewStats> convertViewStatesFromObject(List<Object[]> list) {
        List<ViewStats> result = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            list.stream()
                    .map(object -> result.add(new ViewStats(
                            (String) object[0],
                            (String) object[1],
                            ((BigInteger) object[2]).longValue()))).toArray();
        }
        return result;
    }

}
