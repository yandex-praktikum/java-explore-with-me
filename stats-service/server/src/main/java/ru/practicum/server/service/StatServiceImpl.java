package ru.practicum.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatInfoDto;
import ru.practicum.server.mapper.EndpointHitMapper;
import ru.practicum.server.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private final StatRepository repository;

    @Override
    public void addHitInfo(HitDto hitDto) {

        repository.save(EndpointHitMapper.toEndpointHit(hitDto));
    }

    @Override
    public List<StatInfoDto> getStatInfo(LocalDateTime start, LocalDateTime end, List<String> uris,
                                         boolean unique) {

        List<StatInfoDto> stats;
        List<StatInfoDto> response = new ArrayList<>();
        if (unique) {
            stats = repository.getUniqueStat(start, end);
            for (StatInfoDto stat : stats) {
                for (String uri : uris) {
                    if (stat.getUri().contains(uri)) {
                        response.add(stat);
                    }
                }
            }
        } else {
            stats = repository.getNoUniqueStat(start, end);
            for (StatInfoDto stat : stats) {
                for (String uri : uris) {
                    if (stat.getUri().contains(uri)) {
                        response.add(stat);
                    }
                }
            }
        }
        return response;
    }

}