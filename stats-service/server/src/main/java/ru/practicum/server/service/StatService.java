package ru.practicum.server.service;

import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatInfoDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {
    void addHitInfo(HitDto requestDto);

    List<StatInfoDto> getStatInfo(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);

}