package ru.practicum.ewm.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.*;
import ru.practicum.ewm.dto.newDto.NewEventDto;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.enums.EventStatus;
import ru.practicum.ewm.model.enums.RequestStatus;
import ru.practicum.ewm.model.enums.SortEnum;
import ru.practicum.ewm.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    //***ADMIN METHOD'S***
    @Override
    public List<EventFullDto> getAllEventsForAdm(List<Long> users, List<EventStatus> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {
        return null;
    }

    @Override
    public EventFullDto admUpdateEvent(long eventId, AdminUpdateEventRequestDto updEvent) {
        return null;
    }

    @Override
    public EventFullDto setStatusEvent(long eventId, EventStatus b) {
        return null;
    }

    //***PRIVATE METHOD'S***
    @Override
    public List<EventShortDto> getAllByUserId(long userId, int from, int size) {
        return null;
    }

    @Override
    public EventFullDto updateEvent(long userId, UpdateEventRequestDto updEvent) {
        return null;
    }

    @Override
    public EventFullDto create(long userId, NewEventDto newEvent) {
        return null;
    }

    @Override
    public EventFullDto getEventByIdAndUserId(long userId, long eventId) {
        return null;
    }

    @Override
    public EventFullDto cancelEvent(long userId, long eventId) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getParticipationRequests(long userId, long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto setStatusParticipationRequest(long userId, long eventId, long reqId, RequestStatus status) {
        return null;
    }

    //***PUBLIC METHOD'S***

    @Override
    public Collection<EventShortDto> getAll(String text, List<Category> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, SortEnum sort, int from, int size) {
        return null;
    }

    @Override
    public EventFullDto get(long eventId, HttpServletRequest httpServletRequest) {
        return null;
    }
}
