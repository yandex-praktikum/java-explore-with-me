package ru.practikum.explorewithme.model;

import static ru.practikum.explorewithme.util.ValidDateTime.getDateTime;

public class EndpointHitMapper {

    public static EndpointHit toModel(EndpointHitDto dto) {
        return EndpointHit.builder()
                .app(dto.getApp())
                .uri(dto.getUri())
                .ip(dto.getIp())
                .timestamp(getDateTime(dto.getTimestamp()))
                .build();
    }
}
