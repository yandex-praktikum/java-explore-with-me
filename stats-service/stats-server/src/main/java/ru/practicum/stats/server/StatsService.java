package ru.practicum.stats.server;

import org.springframework.stereotype.Service;
import ru.practicum.stats.dto.HitDto;
import ru.practicum.stats.dto.StatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatsService {

    private final HitRepository hitRepository;

    public StatsService(HitRepository hitRepository) {
        this.hitRepository = hitRepository;
    }

    public void saveHit(HitDto hitDto) {
        Hit hit = convertToEntity(hitDto);
        hitRepository.save(hit);
    }

    private Hit convertToEntity(HitDto hitDto) {
        Hit hit = new Hit();
        hit.setApp(hitDto.getApp());
        hit.setUri(hitDto.getUri());
        hit.setIp(hitDto.getIp());
        hit.setTimestamp(hitDto.getTimestamp());
        return hit;
    }

    public List<StatsDto> getStats(String start, String end, List<String> uris, boolean unique) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime startTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endTime = LocalDateTime.parse(end, formatter);

        List<Object[]> results;
        if (unique) {
            results = hitRepository.findStatsUnique(startTime, endTime, uris);
        } else {
            results = hitRepository.findStatsNonUnique(startTime, endTime, uris);
        }

        return results.stream()
                .map(result -> new StatsDto((String) result[0], (String) result[1], (Long) result[2]))
                .collect(Collectors.toList());
    }

}
