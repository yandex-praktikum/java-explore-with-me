package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.EndpointHitDto;
import ru.practicum.ViewStatsDto;
import ru.practicum.exception.BadRequestException;
import ru.practicum.model.EndpointHit;
import ru.practicum.mapper.EndpointHitMapper;
import ru.practicum.model.ViewStats;
import ru.practicum.mapper.ViewStatsMapper;
import ru.practicum.repository.EndpointHitsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final EndpointHitsRepository endpointHitsRepository;
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @Transactional
    public void createHit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = EndpointHitMapper.fromEndpointHitDto(endpointHitDto);
        endpointHitsRepository.save(endpointHit);
    }

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {
        validateDateRange(start, end);
        List<ViewStats> result = unique ? getStatsUnique(start, end, uris) : getAll(start, end, uris);
        return result.stream()
                .map(ViewStatsMapper::toViewStatsDto)
                .collect(Collectors.toList());
    }


    private List<ViewStats> getStatsUnique(LocalDateTime start, LocalDateTime end, String[] uris) {
        return (uris == null) ?
                endpointHitsRepository.getStatsUnique(start, end) :
                endpointHitsRepository.getStatsUnique(start, end, uris);
    }

    private void validateDateRange(LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(start)) {
            log.error("Дата окончания не может быть раньше даты начала");
            throw new BadRequestException("Дата окончания не может быть раньше даты начала");
        }
    }

    private List<ViewStats> getAll(LocalDateTime start, LocalDateTime end, String[] uris) {
        return (uris == null) ?
                endpointHitsRepository.getAll(start, end) :
                endpointHitsRepository.getAll(start, end, uris);
    }
}