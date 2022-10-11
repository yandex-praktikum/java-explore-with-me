package ru.practicum.ewmservice.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.client.EventClient;
import ru.practicum.ewmservice.dto.*;
import ru.practicum.ewmservice.dto.newDto.NewEventDto;
import ru.practicum.ewmservice.exceptions.*;
import ru.practicum.ewmservice.model.Category;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.ParticipationRequest;
import ru.practicum.ewmservice.model.enums.EventStatus;
import ru.practicum.ewmservice.model.enums.RequestStatus;
import ru.practicum.ewmservice.model.enums.SortEnum;
import ru.practicum.ewmservice.repository.EventGetAllRepository;
import ru.practicum.ewmservice.repository.EventRepository;
import ru.practicum.ewmservice.repository.PartRequestRepository;
import ru.practicum.ewmservice.service.EventService;
import ru.practicum.ewmservice.service.mapper.EventMapper;
import ru.practicum.ewmservice.service.mapper.ParticipationRequestMapper;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private EventRepository repository;
    private EventGetAllRepository getAllRepository;
    private PartRequestRepository partRequestRepository;
    private EventClient client;
    EventMapper mapper;

    //***ADMIN METHOD'S*** ↓
    @Override
    public List<EventFullDto> getAllEventsForAdm(List<Long> users,
                                                 List<EventStatus> states,
                                                 List<Long> categories,
                                                 LocalDateTime rangeStart,
                                                 LocalDateTime rangeEnd,
                                                 int from,
                                                 int size) {
        return getAllRepository.getAllEventsForAdm(users, states, categories, rangeStart, rangeEnd, from, size)
                .stream()
                .map(mapper::toFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto admUpdateEvent(long eventId, AdminUpdateEventRequestDto updEvent) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new EwmObjNotFoundException(String.format("Event with id=%d was not found", eventId)));

        if (updEvent.getLocation() != null) {
            event.setLat(updEvent.getLocation().getLat());
            event.setLat(updEvent.getLocation().getLon());
        }

        if (updEvent.getRequestModeration() != null) {
            event.setRequestModeration(updEvent.getRequestModeration());
        }

        return mapper.toFullDto(repository.save(updateEvent(event, mapper.toEventFromAdmUpdateDto(updEvent))));
    }

    @Override
    public EventFullDto setStatusEvent(long eventId, EventStatus status) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new EwmObjNotFoundException(String.format("Event with id=%d was not found", eventId)));

        switch (status) {
            case CANCELED:
                if (event.getEventStatus() != EventStatus.PUBLISHED) {
                    event.setEventStatus(status);
                } else {
                    throw new EventStatusException("Only pending or canceled events can be changed");
                }
                break;
            case PUBLISHED:
                if (event.getEventStatus() == EventStatus.PENDING) {
                    if (event.getEventDate().isAfter(LocalDateTime.now().plusHours(1))) {
                        event.setEventStatus(status);
                    } else {
                        throw new EventDateTimeException("You cannot publish an event if it is less than 1 hour before the start");
                    }
                } else {
                    throw new EventStatusException("Only pending events can be published");
                }
                break;
        }
        return mapper.toFullDto(event);
    }

    //***PRIVATE METHOD'S*** ↓
    @Override
    public List<EventShortDto> getAllByUserId(long userId, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return repository.findAllByInitiatorId(userId, pageable)
                .stream()
                .map(mapper::toShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEvent(long userId, UpdateEventRequestDto updEvent) {
        Event event = repository.findById(updEvent.getEventId())
                .orElseThrow(() -> new EwmObjNotFoundException(String.format("Event with id=%d was not found", updEvent.getEventId())));

        if (!Objects.equals(userId, event.getInitiator().getId())) {
            throw new EventInitiatorException("You are not an event initiator");
        }

        if (event.getEventStatus() == EventStatus.PUBLISHED) {
            throw new EventStatusException("You cannot change a published event");
        }

        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new EventDateTimeException("Event date is too early, there should be a difference of at least 2 hours");
        }

        if (event.getEventStatus() == EventStatus.CANCELED) {
            event.setEventStatus(EventStatus.PENDING);
        }

        return mapper.toFullDto(repository.save(updateEvent(event, mapper.toEventFromUpdDto(updEvent))));
    }

    @Override
    public EventFullDto create(long userId, NewEventDto newEvent) {

        if (newEvent.getEventDate().isBefore(newEvent.getCreatedOn().plusHours(2))) {
            throw new EventDateTimeException("Should be at least 2 hours difference between the creation date and the event date");
        }

        return mapper.toFullDto(repository.save(mapper.fromNewDtoToEvent(userId, newEvent)));
    }

    @Override
    public EventFullDto getEventByIdAndUserId(long userId, long eventId) {
        Event event = repository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new EwmObjNotFoundException(
                        String.format("User with id=%d does not have an event with id=%d", userId, eventId)));
        return mapper.toFullDto(event);
    }

    @Override
    public EventFullDto cancelEvent(long userId, long eventId) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new EwmObjNotFoundException(String.format("Event with id=%d was not found", eventId)));

        if (event.getEventStatus() != EventStatus.PENDING) {
            throw new EventStatusException(String.format("You can canceled event only with status=%s", event.getEventStatus()));
        }

        event.setEventStatus(EventStatus.CANCELED);

        return mapper.toFullDto(event);
    }

    @Override
    public List<ParticipationRequestDto> getParticipationRequests(long userId, long eventId) {
        getEventByIdAndUserId(userId, eventId);

        return partRequestRepository.findAllByEventId(eventId)
                .stream()
                .map(ParticipationRequestMapper::toPartDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto setStatusParticipationRequest(long userId,
                                                                 long eventId,
                                                                 long reqId,
                                                                 RequestStatus status) {
        EventFullDto event = getEventByIdAndUserId(userId, eventId);

        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            throw new RequestModerationException("Request does not require approval");
        }

        ParticipationRequest request = partRequestRepository.findById(reqId)
                .orElseThrow(() -> new EwmObjNotFoundException(String.format("Participant request with id=%d was not found", reqId)));

        request.setStatus(status);

        return ParticipationRequestMapper.toPartDto(partRequestRepository.save(request));
    }

    //***PUBLIC METHOD'S*** ↓
    @Override
    public Collection<EventShortDto> getAll(String text,
                                            List<Category> categories,
                                            Boolean paid, LocalDateTime rangeStart,
                                            LocalDateTime rangeEnd,
                                            Boolean onlyAvailable,
                                            SortEnum sort,
                                            int from,
                                            int size) {
        return getAllRepository.getAllEvents(text,
                        categories,
                        paid,
                        rangeStart,
                        rangeEnd,
                        onlyAvailable,
                        sort,
                        from,
                        size)
                .stream()
                .map(mapper::toShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto get(long eventId, HttpServletRequest httpServletRequest) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new EwmObjNotFoundException(String.format("Event with id=%d was not found", eventId)));

        if (event.getEventStatus() != EventStatus.PUBLISHED) {
            throw new EventStatusException("Event not published");
        }

        client.addHit(httpServletRequest);

        return mapper.toFullDto(event);
    }


    private Event updateEvent(Event event, Event updEvent) {

        if (updEvent.getAnnotation() != null) {
            event.setAnnotation(updEvent.getAnnotation());
        }

        if (updEvent.getDescription() != null) {
            event.setDescription(updEvent.getDescription());
        }

        if (updEvent.getTitle() != null) {
            event.setTitle(updEvent.getTitle());
        }

        if (updEvent.getCategory() != null) {
            event.setCategory(updEvent.getCategory());
        }

        if (updEvent.getParticipantLimit() != null) {
            event.setParticipantLimit(updEvent.getParticipantLimit());
        }

        if (updEvent.getEventDate() != null) {
            event.setEventDate(updEvent.getEventDate());
        }

        if (updEvent.getPaid() != null) {
            event.setPaid(updEvent.getPaid());
        }

        return event;
    }
}
