package ru.practicum.stats;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class StatsServiceImpl implements StatsService {

    private StatsRepository statsRepository;

    @Transactional()
    @Override
    public void saveHit(HitDto hitDto) {
        Hit hit = Hit.builder()
                .uri(hitDto.getUri())
                .ip(hitDto.getIp())
                .app(hitDto.getApp())
                .timestamp(hitDto.getTimestamp())
                .build();
        statsRepository.save(hit);
    }

    @Override
    public List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {

        if (unique) {
            if (uris == null || uris.isEmpty()) {
                return statsRepository.getListOfUniqueStatistics(start, end);
            } else {
                return statsRepository.getUniqueStatisticsByUrisAndTimes(start, end, uris);
            }
        } else {
            if (uris == null || uris.isEmpty()) {
                return statsRepository.getListAllStats(start, end);
            } else {
                return statsRepository.getStatisticsByUrlsAndTimes(start, end, uris);
            }
        }
    }
}