package ru.practicum;

import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface HitService {

    void addHit(HitDto hitDto);

    List<StatsDto> findStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
