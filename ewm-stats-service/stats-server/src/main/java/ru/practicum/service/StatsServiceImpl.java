package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.EndpointHitDto;
import ru.practicum.mapper.EndpointHitMapper;
import ru.practicum.model.ViewStats;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {
    private final StatsRepository repository;

    @Override
    @Transactional
    public void saveHit(EndpointHitDto hitDto) {
        repository.save(EndpointHitMapper.toHit(hitDto));
    }

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris) {
        validateDateOrder(start, end);
        if (uris.isEmpty()) {
            return repository.getNotUniqueStatsWithoutUris(start, end);
        }
        return repository.getNotUniqueStats(start, end, uris);
    }

    @Override
    public List<ViewStats> getUniqueStats(LocalDateTime start, LocalDateTime end, List<String> uris) {
        validateDateOrder(start, end);
        if (uris.isEmpty()) {
            return repository.getUniqueStatsWithoutUris(start, end);
        }
        return repository.getUniqueStats(start, end, uris);
    }

    private void validateDateOrder(LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(start)) {
            throw new RuntimeException("Дата конца не может быть перед датой начала.");
        }
    }
}
