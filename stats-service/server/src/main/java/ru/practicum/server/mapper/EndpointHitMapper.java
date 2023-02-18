package ru.practicum.server.mapper;

import ru.practicum.dto.HitDto;
import ru.practicum.server.model.EndpointHit;

public class EndpointHitMapper {

    public static EndpointHit toEndpointHit(HitDto hitDto) {
        return EndpointHit.builder()
                .app(hitDto.getApp())
                .uri(hitDto.getUri())
                .ip(hitDto.getIp())
                .timestamp(hitDto.getTimestamp())
                .build();
    }

    public static HitDto toStatInfoDto(EndpointHit endpointHit) {
        return HitDto.builder()
                .id(endpointHit.getId())
                .app(endpointHit.getApp())
                .uri(endpointHit.getUri())
                .ip(endpointHit.getIp())
                .timestamp(endpointHit.getTimestamp())
                .build();
    }
}