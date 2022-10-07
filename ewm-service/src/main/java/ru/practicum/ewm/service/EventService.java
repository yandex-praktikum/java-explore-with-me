package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.*;
import ru.practicum.ewm.dto.newDto.NewEventDto;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.enums.EventStatus;
import ru.practicum.ewm.model.enums.RequestStatus;
import ru.practicum.ewm.model.enums.SortEnum;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface EventService {
    //***ADMIN METHOD'S*** ↓
    List<EventFullDto> getAllEventsForAdm(List<Long> users,
                                          List<EventStatus> states,
                                          List<Long> categories,
                                          LocalDateTime rangeStart,
                                          LocalDateTime rangeEnd,
                                          int from,
                                          int size);

    EventFullDto admUpdateEvent(long eventId, AdminUpdateEventRequestDto updEvent);

    EventFullDto setStatusEvent(long eventId, EventStatus b);

    //***PRIVATE METHOD'S*** ↓
    List<EventShortDto> getAllByUserId(long userId, int from, int size);

    EventFullDto updateEvent(long userId, UpdateEventRequestDto updEvent);

    EventFullDto create(long userId, NewEventDto newEvent);

    EventFullDto getEventByIdAndUserId(long userId, long eventId);

    EventFullDto cancelEvent(long userId, long eventId);

    List<ParticipationRequestDto> getParticipationRequests(long userId, long eventId);

    ParticipationRequestDto setStatusParticipationRequest(long userId, long eventId, long reqId, RequestStatus status);

    //***PRIVATE METHOD'S*** ↓
    Collection<EventShortDto> getAll(String text,
                                     List<Category> categories,
                                     Boolean paid,
                                     LocalDateTime rangeStart,
                                     LocalDateTime rangeEnd,
                                     Boolean onlyAvailable,
                                     SortEnum sort,
                                     int from,
                                     int size);


    EventFullDto get(long eventId, HttpServletRequest httpServletRequest);
}
