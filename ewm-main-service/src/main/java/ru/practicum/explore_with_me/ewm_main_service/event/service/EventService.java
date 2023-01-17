package ru.practicum.explore_with_me.ewm_main_service.event.service;

import ru.practicum.explore_with_me.ewm_main_service.event.dto.*;
import ru.practicum.explore_with_me.ewm_main_service.event.model.Event;
import ru.practicum.explore_with_me.ewm_main_service.event.model.EventSortEnum;
import ru.practicum.explore_with_me.ewm_main_service.event.model.EventStateEnum;
import ru.practicum.explore_with_me.ewm_main_service.exceptions.AccessForbiddenException;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    List<Event> searchEvents(String text, Long[] categoryIds, Boolean paid, LocalDateTime rangeStart,
                             LocalDateTime rangeEnd, Boolean onlyAvailable, EventSortEnum sort,
                             Integer from, Integer size);

    List<EventShortDto> searchEvents(String text, Long[] categoryIds, Boolean paid, String rangeStart,
                                     String rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size,
                                     String role) throws AccessForbiddenException;

    Event getEvent(Long eventId);

    EventShortDto getEvent(Long eventId, String role) throws AccessForbiddenException;

    Event getEvent(Long eventId, Long userId);

    EventFullDto getEvent(Long eventId, Long userId, String role) throws AccessForbiddenException;

    Event createEvent(Long userId, Event event);

    EventFullDto createEvent(Long userId, NewEventDto eventDto, String role) throws AccessForbiddenException;

    Event editEvent(Event event, Long userId);

    EventFullDto editEvent(UpdateEventDto event, Long userId, String role) throws AccessForbiddenException;

    Event editEvent(Long eventId, Event event);

    EventFullDto editEvent(AdminUpdateEventDto eventDto, Long eventId, String role) throws AccessForbiddenException;

    Event cancelEvent(Long eventId, Long userId);

    EventFullDto cancelEvent(Long eventId, Long userId, String role) throws AccessForbiddenException;


    List<Event> findEvent(Long[] userIds, EventStateEnum[] states, Long[] categoryIds, LocalDateTime rangeStart,
                          LocalDateTime rangeEnd, Integer from, Integer size, Long userId);

    List<Event> findEvent(Long userId, Integer from, Integer size);

    List<EventFullDto> findEvent(Long[] userIds, String[] states, Long[] categoryIds, String rangeStart,
                                 String rangeEnd, Integer from, Integer size, Long userId, String role)
            throws AccessForbiddenException;

    Event publishEvent(Long eventId);

    EventFullDto publishEvent(Long eventId, String role) throws AccessForbiddenException;

    Event rejectEvent(Long eventId);

    EventFullDto rejectEvent(Long eventId, String role) throws AccessForbiddenException;

}
