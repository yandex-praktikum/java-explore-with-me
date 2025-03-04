package ru.practicum.hits;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.HitRequest;
import ru.practicum.exception.IncorrectRequestException;
import ru.practicum.stats.Stat;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HitService {
    private final HitRepository repo;
    private final HitMapper mapper;

    public Hit save(HitRequest request) {
        return repo.save(mapper.toEntity(request));
    }

    public List<Stat> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        log.info("Upcoming getStats request(start: %s, end: %s, uris: %s, unique: %s",
                start, end, uris, unique);
        if (start.isAfter(end)) {
            throw new IncorrectRequestException("Первая граница временного интервала не может быть больше второй");
        }

        List<Stat> result;
        if (uris == null || uris.isEmpty()) {
            result = unique ? repo.getStatsInIntervalAndAppAndUriWithUniqueIpDesc(start, end)
                            : repo.getStatsInIntervalGroupedByAppAndUriDesc(start, end);
        } else {
            result = unique ? repo.getStatsInIntervalAndAppAndUrisWithUniqueIpDesc(start, end, uris)
                            : repo.getStatsInIntervalAndUrisGroupedByAppAndUriDesc(start, end, uris);
        }

        log.info("response: %s", result);
        return result;
    }
}
