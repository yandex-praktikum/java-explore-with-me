package ru.practicum.stats.mappers;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.stats.model.ViewStats;

@UtilityClass
public class ViewStatsMapper {
    public ViewStatsDto toViewstatsDto(ViewStats viewStats) {
        ViewStatsDto viewStatsDto = new ViewStatsDto();
        viewStatsDto.setApp(viewStats.getApp());
        viewStatsDto.setUri(viewStats.getUri());
        viewStatsDto.setHits(viewStats.getHits());
        return viewStatsDto;
    }
}
