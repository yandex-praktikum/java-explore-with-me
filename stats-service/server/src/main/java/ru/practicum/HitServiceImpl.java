package ru.practicum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;

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
    public void addHit(HitDto hitDto) {

        hitRepository.save(HitMapper.returnHit(hitDto));
    }

    @Override
    public List<StatsDto> findStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {

        if (uris == null || uris.isEmpty()) {
            if (unique) {
                log.info("Get all stats by uniq ip");
                return hitRepository.findAllStatsByUniqIp(start, end);
            } else {
                log.info("Get all stats");
                return hitRepository.findAllStats(start, end);
            }
        } else {
            if (unique) {
                log.info("Get all stats by uri and uniq ip");
                return hitRepository.findStatsByUrisByUniqIp(start, end, uris);
            } else {
                log.info("Get all stats by uri");
                return hitRepository.findStatsByUris(start, end, uris);
            }
        }
    }
}
