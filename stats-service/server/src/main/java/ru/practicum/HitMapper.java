package ru.practicum;

import ru.practicum.dto.HitDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HitMapper {

    public static HitDto returnHitDto(Hit hit) {

        HitDto hitDto = HitDto.builder()
                .id(hit.getId())
                .app(hit.getApp())
                .uri(hit.getUri())
                .ip(hit.getIp())
                .timestamp(hit.getTimestamp().toString())
                .build();
        return hitDto;
    }

    public static Hit returnHit(HitDto hitDto) {

        Hit hit = Hit.builder()
                .id(hitDto.getId())
                .app(hitDto.getApp())
                .uri(hitDto.getUri())
                .ip(hitDto.getIp())
                .timestamp(LocalDateTime.parse(hitDto.getTimestamp(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        return hit;
    }

    public static List<HitDto> returnHitDtoList(Iterable<Hit> hits) {
        List<HitDto> result = new ArrayList<>();

        for (Hit hit : hits) {
            result.add(returnHitDto(hit));
        }
        return result;
    }
}
