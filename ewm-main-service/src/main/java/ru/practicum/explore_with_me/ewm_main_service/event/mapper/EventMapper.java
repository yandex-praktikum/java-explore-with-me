package ru.practicum.explore_with_me.ewm_main_service.event.mapper;

import ru.practicum.explore_with_me.ewm_main_service.category.model.Category;
import ru.practicum.explore_with_me.ewm_main_service.event.dto.*;
import ru.practicum.explore_with_me.ewm_main_service.event.model.Event;

import java.time.format.DateTimeFormatter;

public interface EventMapper {
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    Event toEvent(NewEventDto dto, Category category);

    Event toEvent(UpdateEventDto dto, Category category);

    Event toEvent(AdminUpdateEventDto dto, Category category);

    EventFullDto toDto(Event event, Long confirmedRequests);

    EventShortDto toShortDto(Event event, Long confirmedRequests);

}
