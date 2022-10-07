package ru.practicum.stats.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.stats.dto.HitDto;
import ru.practicum.stats.model.ViewStats;
import ru.practicum.stats.repository.StatRepository;
import ru.practicum.stats.repository.StatsParamRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class StatServiceImpl implements StatService {
    private StatRepository repository;

    private StatsParamRepository paramRepository;

    @Override
    public void save(HitDto dto) {
        repository.save(StatMapper.toHit(dto));
    }

    @Override
    public Integer getViews(String uri) {
        return repository.countByUri(uri);
    }

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        return paramRepository.findStatsWithParam(start, end, uris, unique);
    }
}
