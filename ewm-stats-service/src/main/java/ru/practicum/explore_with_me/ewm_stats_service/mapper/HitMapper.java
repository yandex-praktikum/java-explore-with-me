package ru.practicum.explore_with_me.ewm_stats_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.explore_with_me.ewm_stats_service.dto.EndpointHitDto;
import ru.practicum.explore_with_me.ewm_stats_service.model.Hit;

@Mapper
public interface HitMapper {
    String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Mapping(source = "timestamp", target = "timestamp", dateFormat = DATE_TIME_FORMAT)
    Hit toHit(EndpointHitDto endpointHit);

    @Mapping(source = "timestamp", target = "timestamp", dateFormat = DATE_TIME_FORMAT)
    EndpointHitDto toDto(Hit hit);

}
