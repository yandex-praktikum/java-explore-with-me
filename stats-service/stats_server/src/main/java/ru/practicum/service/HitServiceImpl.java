package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.HitDto;
import ru.practicum.StatsDto;
import ru.practicum.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {

    private final HitRepository hitRepository;

    @Transactional
    @Override
    public void createHit(HitDto hitDto) {
        hitRepository.save(HitMapper.makeHitInDto(hitDto));
    }

    @Override
    public List<StatsDto> findStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {

        if (uris == null || uris.isEmpty()) {
            if (Boolean.TRUE.equals(unique)) {
                log.info("Получение статистики по ip: ");
                return hitRepository.findAllStatsByUniqIp(start, end);
            } else {
                log.info("Получение всей статистики: ");
                return hitRepository.findAllStats(start, end);
            }
        } else {
            if (Boolean.TRUE.equals(unique)) {
                log.info("Получение статсики по uri и ip: ");
                return hitRepository.findStatsByUrisByUniqIp(start, end, uris);
            } else {
                log.info("Получение статсики по uri: ");
                return hitRepository.findStatsByUris(start, end, uris);
            }
        }
    }
}