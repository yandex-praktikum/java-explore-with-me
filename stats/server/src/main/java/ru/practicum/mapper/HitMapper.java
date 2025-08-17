package ru.practicum.mapper;

import ru.practicum.HitDto;
import ru.practicum.model.Hit;

public class HitMapper {
    public static Hit fromHitDto(HitDto hitDto) {
        return new Hit(hitDto.getId(),
                hitDto.getApp(),
                hitDto.getUri(),
                hitDto.getIp(),
                hitDto.getTimestamp());
    }

    public static HitDto toHitDto(Hit hit) {
        return new HitDto(hit.getId(),
                hit.getApp(),
                hit.getUri(),
                hit.getIp(),
                hit.getTimestamp());
    }
}