package ru.practicum.stats.server;

import org.springframework.stereotype.Service;
import ru.practicum.stats.dto.HitDto;
import ru.practicum.stats.dto.StatsDto;

import java.util.List;

@Service
public class StatsService {

    private final HitRepository hitRepository;

    public StatsService(HitRepository hitRepository) {
        this.hitRepository = hitRepository;
    }

    // Метод сохранения запроса
    public void saveHit(HitDto hitDto) {
        Hit hit = convertToEntity(hitDto);
        hitRepository.save(hit);
    }

    // Метод конвертации DTO -> Entity
    private Hit convertToEntity(HitDto hitDto) {
        Hit hit = new Hit();
        hit.setApp(hitDto.getApp());
        hit.setUri(hitDto.getUri());
        hit.setIp(hitDto.getIp());
        // Прямое присваивание, так как уже LocalDateTime
        hit.setTimestamp(hitDto.getTimestamp());
        return hit;
    }

    // Метод получения статистики (заглушка)
    public List<StatsDto> getStats(String start, String end, List<String> uris, boolean unique) {
        return List.of();
    }
}
