package ru.explorewithme.users.events;

import ru.explorewithme.admin.categories.CategoryMapper;
import ru.explorewithme.admin.model.Category;
import ru.explorewithme.user.model.User;
import ru.explorewithme.user.UserMapper;
import ru.explorewithme.users.dto.EventFullDto;
import ru.explorewithme.users.dto.EventShortDto;
import ru.explorewithme.users.dto.NewEventDto;
import ru.explorewithme.users.model.Event;
import ru.explorewithme.users.model.Location;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventMapper {
    public static Event toEvent(User user, NewEventDto newEventDto, Category category) {
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .category(category)
                .createdOn(LocalDateTime.now())
                .description(newEventDto.getDescription())
                .eventDate(newEventDto.getEventDate())
                .initiator(user)
                .locationLat(newEventDto.getLocation().getLat())
                .locationLon(newEventDto.getLocation().getLon())
                .paid(newEventDto.getPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .publishedOn(LocalDateTime.now())
                .requestModeration(newEventDto.getRequestModeration())
                .state("PENDING")
                .title(newEventDto.getTitle())
                .build();
    }

    public static EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder()
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(0L)
                .createdOn(event.getCreatedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .description(event.getDescription())
                .eventDate(fromDateToString(event.getEventDate()))
                .id(event.getId())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .location(new Location(event.getLocationLat(), event.getLocationLon()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn("dd")
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(9L)
                .build();
    }

    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(0L)
                .eventDate(event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .id(event.getId())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(9L)
                .build();
    }

    public static LocalDateTime fromStringToLocalDateTime(String s) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return LocalDateTime.parse(s, formatter);
    }

    public static String fromDateToString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String s = date.format(formatter);
        return s;
    }

    public static List<EventShortDto> mapToEventShortDto(Iterable<Event> events) {
        List<EventShortDto> dtos = new ArrayList<>();
        for (Event event : events) {
            dtos.add(toEventShortDto(event));
        }
        return dtos;
    }

    public static List<EventFullDto> mapToEventFullDto(Iterable<Event> events) {
        List<EventFullDto> dtos = new ArrayList<>();
        for (Event event : events) {
            dtos.add(toEventFullDto(event));
        }
        return dtos;
    }

    /*public static Event toEvent(Long eventId, AdminUpdateEventRequest updateEvent, Category category) {
        return Event.builder()
                .id(eventId)
                .annotation(updateEvent.getAnnotation())
                .category(category)
                .description(updateEvent.getDescription())
                .eventDate(updateEvent.getEventDate())
                .locationLat(updateEvent.getLocation().getLat())
                .locationLon(updateEvent.getLocation().getLon())
                .paid(updateEvent.getPaid())
                .participantLimit(updateEvent.getParticipantLimit())
                .requestModeration(updateEvent.getRequestModeration())
                .title(updateEvent.getTitle())
                .build();
    }*/
}
