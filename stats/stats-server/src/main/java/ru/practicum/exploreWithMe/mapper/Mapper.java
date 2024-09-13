package ru.practicum.exploreWithMe.mapper;

import ru.practicum.exploreWithMe.dto.EndpointHitDto;
import ru.practicum.exploreWithMe.dto.ViewStatsDto;
import ru.practicum.exploreWithMe.model.EndpointHit;
import ru.practicum.exploreWithMe.model.ViewStats;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Mapper {
    public static EndpointHit toEndpointHit(EndpointHitDto hitDto) {
        EndpointHit hit = new EndpointHit();
        hit.setApp(hitDto.getApp());
        hit.setUri(hitDto.getUri());
        hit.setIp(hitDto.getIp());
        hit.setTimestamp(hitDto.getTimestamp());
        return hit;
    }

    public static EndpointHitDto toEndpointHitDto(EndpointHit hit) {
        return new EndpointHitDto(hit.getId(), hit.getApp(), hit.getUri(), hit.getIp(), hit.getTimestamp());
    }

    public static List<ViewStatsDto> toViewStatsDtoList(List<ViewStats> stats) {
        List<ViewStatsDto> statsDroList = new ArrayList<>();
        for (ViewStats viewStats : stats) {
            statsDroList.add(toViewStatsDto(viewStats));
        }
        statsDroList.sort(Comparator.comparingLong(ViewStatsDto::getHits).reversed());
        return statsDroList;
    }

    private static ViewStatsDto toViewStatsDto(ViewStats viewStats) {
        return new ViewStatsDto(viewStats.getApp(), viewStats.getUri(), viewStats.getHits());
    }
}
