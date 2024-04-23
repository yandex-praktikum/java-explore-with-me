package ru.practicum.ewm.stats.mapper;

import ru.practicum.dto.EndpointHit;
import ru.practicum.ewm.stats.model.Hit;

import java.time.LocalDateTime;

import static ru.practicum.ewm.stats.utils.Constant.DATE_TIME_FORMATTER;

public class HitMapper {


    public static Hit toHit(EndpointHit endpointHit) {
        return Hit.builder()
                .id(endpointHit.getId())
                .app(endpointHit.getApp())
                .uri(endpointHit.getUri())
                .ip(endpointHit.getIp())
                .timestamp(LocalDateTime.parse(endpointHit.getTimestamp(), DATE_TIME_FORMATTER))
                .build();
    }

    public static EndpointHit toEndpointHit(Hit hit) {
        return EndpointHit.builder()
                .id(hit.getId())
                .app(hit.getApp())
                .uri(hit.getUri())
                .ip(hit.getIp())
                .timestamp(hit.getTimestamp().format(DATE_TIME_FORMATTER))
                .build();
    }

}
