package ru.practicum.explore_with_me.ewm_main_service.event.mapper;

import org.mapstruct.Mapper;
import ru.practicum.explore_with_me.ewm_main_service.event.dto.LocationDto;
import ru.practicum.explore_with_me.ewm_main_service.event.model.Location;

@Mapper
public interface LocationMapper {

    LocationDto toDto(Location location);

}
