package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.InputEventDto;
import ru.practicum.dto.OutputStatsDto;
import ru.practicum.model.Stats;
import ru.practicum.model.StatsMapper;
import ru.practicum.repository.StatisticsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsRepository repository;
    private final StatsMapper mapper;

    @Override
    public InputEventDto saveEventStats(InputEventDto dto) {
        Stats stats = mapper.mapInputEventDtoToStats(dto);
        stats.setRequestDate(LocalDateTime.now());
        return mapper.mapStatsToInputEventDto(repository.saveAndFlush(stats));
    }

    public List<OutputStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if (unique) {
            return repository.findUniqueStats(start, end, uris);
        } else {
            return repository.findStats(start, end, uris);
        }
    }
}
