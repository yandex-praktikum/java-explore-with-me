package ru.practicum.exploreWithMe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.exploreWithMe.dto.EndpointHitDto;
import ru.practicum.exploreWithMe.dto.ViewStatsDto;
import ru.practicum.exploreWithMe.mapper.Mapper;
import ru.practicum.exploreWithMe.model.EndpointHit;
import ru.practicum.exploreWithMe.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository repository;

    @Override
    public EndpointHitDto addHit(EndpointHitDto endpointHitDto) {
        EndpointHit savedHit = repository.save(Mapper.toEndpointHit(endpointHitDto));
        return Mapper.toEndpointHitDto(savedHit);
    }

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if (unique) {
            if (uris == null || uris.isEmpty()) {
                return Mapper.toViewStatsDtoList(repository.findUniqueIpStatsBetweenDates(start, end));
            } else {
                return Mapper.toViewStatsDtoList(repository.findUniqueIpStatsBetweenDatesAndUris(start, end, uris));
            }
        } else {
            if (uris == null || uris.isEmpty()) {
                return Mapper.toViewStatsDtoList(repository.findStatsBetweenDates(start, end));
            } else {
                return Mapper.toViewStatsDtoList(repository.findStatsBetweenDatesAndUris(start, end, uris));
            }
        }
    }
}
