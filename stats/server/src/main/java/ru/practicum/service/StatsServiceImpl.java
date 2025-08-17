package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.HitDto;
import ru.practicum.StatsDto;
import ru.practicum.mapper.HitMapper;
import ru.practicum.model.Hit;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    @Override
    public HitDto addHit(HitDto hitDto) {
        Hit hit = HitMapper.fromHitDto(hitDto);
        return HitMapper.toHitDto(statsRepository.save(hit));
    }

    @Override
    public List<StatsDto> getStats(String start,
                                   String end,
                                   List<String> uris,
                                   Boolean unique) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endTime = LocalDateTime.parse(end, formatter);
        if (uris != null) {
            if (unique) {
                return statsRepository.findStatsByUriAndStartAndEndUnique(uris, startTime, endTime);
            } else {
                return statsRepository.findStatsByUriAndStartAndEnd(uris, startTime, endTime);
            }
        } else {
            if (unique) {
                return statsRepository.findStatsStartAndEndUnique(startTime, endTime);
            } else {
                return statsRepository.findStatsStartAndEnd(startTime, endTime);
            }
        }
    }
}