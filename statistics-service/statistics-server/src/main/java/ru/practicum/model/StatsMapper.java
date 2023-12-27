package ru.practicum.model;

import org.mapstruct.Mapper;
import ru.practicum.dto.InputEventDto;

@Mapper(componentModel = "spring")
public interface StatsMapper {
    Stats mapInputEventDtoToStats(InputEventDto dto);

    InputEventDto mapStatsToInputEventDto(Stats stats);
}
