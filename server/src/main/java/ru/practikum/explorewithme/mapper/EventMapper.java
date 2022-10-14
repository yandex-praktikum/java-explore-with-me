package ru.practikum.explorewithme.mapper;

import ru.practikum.explorewithme.dto.EventFullDto;
import ru.practikum.explorewithme.dto.EventShortDto;
import ru.practikum.explorewithme.dto.in.NewEventDto;
import ru.practikum.explorewithme.model.Category;
import ru.practikum.explorewithme.model.Event;
import ru.practikum.explorewithme.model.Location;

import java.util.ArrayList;
import java.util.List;

import static ru.practikum.explorewithme.util.ValidDateTime.getDateTime;

public class EventMapper {

    public static List<EventShortDto> toShortDto(List<Event> events) {
        List<EventShortDto> list = new ArrayList<>();
        for (Event event : events) {
            list.add(toShortDto(event));
        }
        return list;
    }

    public static EventShortDto toShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .initiator(UserMapper.toShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .views(event.getViews())
                .build();
    }

    public static EventFullDto toFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .description(event.getDescription())
                .category(CategoryMapper.toDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .createdOn(event.getCreatedOn())
                .publishedOn(event.getPublishedOn())
                .initiator(UserMapper.toShortDto(event.getInitiator()))
                .location(event.getLocation())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.isRequestModeration())
                .state(event.getStatus())
                .paid(event.getPaid())
                .views(event.getViews())
                .build();
    }

    public static Event toEvent(NewEventDto in, Category category) {
        return Event.builder()
                .annotation(in.getAnnotation())
                .category(category)
                .description(in.getDescription())
                .eventDate(getDateTime(in.getEventDate()))
                .location(toLocation(in.getLocation()))
                .paid(in.isPaid())
                .participantLimit(in.getParticipantLimit())
                .requestModeration(in.isRequestModeration())
                .title(in.getTitle())
                .build();
    }

    private static Location toLocation(ru.practikum.explorewithme.dto.in.Location locationIn) {
        return Location.builder()
                .lat(locationIn.getLat())
                .lon(locationIn.getLon())
                .build();
    }
}
