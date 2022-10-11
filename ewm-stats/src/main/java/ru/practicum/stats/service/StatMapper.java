package ru.practicum.stats.service;

import ru.practicum.stats.dto.HitDto;
import ru.practicum.stats.model.Hit;

public class StatMapper {

    public static Hit toHit(HitDto dto) {
        Hit hit = new Hit();
        hit.setApp(dto.getApp());
        hit.setTimestamp(dto.getTimestamp());
        hit.setIp(dto.getIp());
        hit.setUri(dto.getUri());

        return hit;
    }
}
