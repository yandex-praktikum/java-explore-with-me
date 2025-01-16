package ru.practicum.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.ViewStats;

@RequiredArgsConstructor
@Component
public class EndpointHitMapper {

    public EndpointHit toEndpointHit(EndpointHitDto endpointHitDto) {
        if (endpointHitDto != null) {
            EndpointHit hit = new EndpointHit();
            hit.setApp(endpointHitDto.getApp());
            hit.setUri(endpointHitDto.getUri());
            hit.setIp(endpointHitDto.getIp());
            hit.setTimestamp(endpointHitDto.getTimestamp());
            return hit;
        } else {
            return null;
        }
    }

    public ViewStats toViewStats(ViewStatsDto viewStatsDto) {
        if (viewStatsDto != null) {
            return new ViewStats(
                    viewStatsDto.getApp(),
                    viewStatsDto.getUri(),
                    1L
            );
        } else {
            return null;
        }
    }

    public ViewStatsDto toViewStatsDto(ViewStats viewStats) {
        if (viewStats != null) {
            return new ViewStatsDto(
                    viewStats.getApp(),
                    viewStats.getUri(),
                    1L
            );
        } else {
            return null;
        }
    }

    public ViewStatsDto toViewStatsDto(EndpointHit endpointHit) {
        return new ViewStatsDto(
                endpointHit.getApp(),
                endpointHit.getUri(),
                0L
        );
    }
}
