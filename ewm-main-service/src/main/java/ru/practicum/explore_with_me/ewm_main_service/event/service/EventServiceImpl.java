package ru.practicum.explore_with_me.ewm_main_service.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.ewm_main_service.category.model.Category;
import ru.practicum.explore_with_me.ewm_main_service.category.repository.CategoryRepository;
import ru.practicum.explore_with_me.ewm_main_service.event.dto.*;
import ru.practicum.explore_with_me.ewm_main_service.event.mapper.EventMapper;
import ru.practicum.explore_with_me.ewm_main_service.event.model.Event;
import ru.practicum.explore_with_me.ewm_main_service.event.model.EventSortEnum;
import ru.practicum.explore_with_me.ewm_main_service.event.model.EventStateEnum;
import ru.practicum.explore_with_me.ewm_main_service.event.model.FilterCollection;
import ru.practicum.explore_with_me.ewm_main_service.event.repository.EventCustomRepository;
import ru.practicum.explore_with_me.ewm_main_service.event.repository.EventRepository;
import ru.practicum.explore_with_me.ewm_main_service.exceptions.AccessForbiddenException;
import ru.practicum.explore_with_me.ewm_main_service.exceptions.ArgumentNotValidException;
import ru.practicum.explore_with_me.ewm_main_service.exceptions.BadRequestException;
import ru.practicum.explore_with_me.ewm_main_service.exceptions.NotFoundException;
import ru.practicum.explore_with_me.ewm_main_service.handler.Error;
import ru.practicum.explore_with_me.ewm_main_service.request.model.RequestStatusEnum;
import ru.practicum.explore_with_me.ewm_main_service.request.repository.RequestRepository;
import ru.practicum.explore_with_me.ewm_main_service.user.model.User;
import ru.practicum.explore_with_me.ewm_main_service.user.repository.UserRepository;
import ru.practicum.explore_with_me.ewm_main_service.utils.RoleEnum;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.explore_with_me.ewm_main_service.event.mapper.EventMapper.DATE_TIME_FORMATTER;
import static ru.practicum.explore_with_me.ewm_main_service.utils.ParametersValid.pageValidated;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventCustomRepository eventCustomRepository;
    private final EventMapper eventMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;

    @Override
    public List<Event> searchEvents(String text, Long[] categoryIds, Boolean paid, LocalDateTime rangeStart,
                                    LocalDateTime rangeEnd, Boolean onlyAvailable, EventSortEnum sort,
                                    Integer from, Integer size) {
        FilterCollection filter = new FilterCollection();
        filter.setText(text);
        filter.setCategories(categoryIds);
        filter.setPaid(paid);
        filter.setRangeStart(rangeStart);
        filter.setRangeEnd(rangeEnd);
        filter.setOnlyAvailable(onlyAvailable != null ? onlyAvailable : false);
        filter.setFrom(from);
        filter.setSize(size);

        List<Event> events = eventCustomRepository.searchEvents(filter);
        if (EventSortEnum.EVENT_DATE.equals(sort)) {
            return events
                    .stream()
                    .peek(this::incrementViews)
                    .sorted(Comparator.comparing(Event::getEventDate))
                    .collect(Collectors.toList());
        } else {
            return events
                    .stream()
                    .peek(this::incrementViews)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<EventShortDto> searchEvents(String text, Long[] categoryIds, Boolean paid, String rangeStart,
                                            String rangeEnd, Boolean onlyAvailable, String sort,
                                            Integer from, Integer size, String role) {
        return searchEvents(text, categoryIds, paid,
                rangeStart != null ? LocalDateTime.parse(rangeStart, DATE_TIME_FORMATTER) : null,
                rangeEnd != null ? LocalDateTime.parse(rangeEnd, DATE_TIME_FORMATTER) : null,
                onlyAvailable,
                sort != null ? EventSortEnum.of(sort) : null,
                from, size)
                .stream()
                .map(e -> eventMapper.toShortDto(e, Long.valueOf(requestRepository
                        .countParticipationByEventIdAndStatus(e.getId(), RequestStatusEnum.CONFIRMED))))
                .collect(Collectors.toList());
    }

    @Override
    public Event getEvent(Long eventId) {
        return getEventOrThrow(eventId);
    }

    @Override
    public EventShortDto getEvent(Long eventId, String role) {
        return eventMapper.toShortDto(getEvent(eventId), Long.valueOf(requestRepository
                .countParticipationByEventIdAndStatus(eventId, RequestStatusEnum.CONFIRMED)));
    }

    @Override
    public Event getEvent(Long eventId, Long userId) {
        Event event = getEventOrThrow(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new AccessForbiddenException(this.getClass().getName(),
                    "Only initiator can get full access to Event",
                    String.format("Event id%d has initiator id%d.", event.getId(), event.getInitiator().getId()));
        }
        incrementViews(event);
        return event;
    }

    @Override
    public EventFullDto getEvent(Long eventId, Long userId, String role) throws AccessForbiddenException {
        if (RoleEnum.of(role) == RoleEnum.USER) {
            return eventMapper.toDto(getEvent(eventId, userId), Long.valueOf(requestRepository
                    .countParticipationByEventIdAndStatus(eventId, RequestStatusEnum.CONFIRMED)));
        } else {
            return eventMapper.toDto(getEvent(eventId), Long.valueOf(requestRepository
                    .countParticipationByEventIdAndStatus(eventId, RequestStatusEnum.CONFIRMED)));
        }
    }

    @Transactional
    @Override
    public Event createEvent(Long userId, Event event) {
        User initiator = getUserOrThrow(userId);
        if (event.getEventDate().isBefore(LocalDateTime.now().minusHours(2))) {
            throw new BadRequestException(this.getClass().getName(),
                    "Date event is too late",
                    String.format("Event id%d has event date %s.", event.getId(), event.getEventDate()));
        }
        event.setInitiator(initiator);
        return eventRepository.save(event);
    }


    @Override
    public EventFullDto createEvent(Long userId, NewEventDto eventDto, String role) throws AccessForbiddenException {
        if (RoleEnum.of(role) == RoleEnum.USER) {
            Category category = categoryRepository.getById(eventDto.getCategory());
            Event event = createEvent(userId, eventMapper.toEvent(eventDto, category));
            return eventMapper.toDto(event, Long.valueOf(requestRepository
                    .countParticipationByEventIdAndStatus(event.getId(), RequestStatusEnum.CONFIRMED)));
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }

    @Transactional
    @Override
    public Event editEvent(Event event, Long userId) throws AccessForbiddenException {
        if (userRepository.existsById(userId)) {
            Event editedEvent = getEventOrThrow(event.getId());
            editedEvent.update(event);
            return eventRepository.save(editedEvent);
        } else {
            throw new AccessForbiddenException(this, userId);
        }
    }

    @Override
    public EventFullDto editEvent(UpdateEventDto eventDto, Long userId, String role) throws
            AccessForbiddenException {
        if (RoleEnum.of(role) == RoleEnum.USER) {
            Category category = categoryRepository.getById(eventDto.getCategory());
            return eventMapper.toDto(editEvent(eventMapper.toEvent(eventDto, category), userId),
                    Long.valueOf(requestRepository
                            .countParticipationByEventIdAndStatus(eventDto.getEventId(), RequestStatusEnum.CONFIRMED)));
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }

    @Transactional
    @Override
    public Event editEvent(Long eventId, Event event) {
        Event editedEvent = getEventOrThrow(eventId);
        editedEvent.update(event);
        return eventRepository.save(editedEvent);
    }

    @Override
    public EventFullDto editEvent(AdminUpdateEventDto eventDto, Long eventId, String role) throws
            AccessForbiddenException {
        if (RoleEnum.of(role) == RoleEnum.ADMINISTRATOR) {
            Category category = categoryRepository.getById(eventDto.getCategory());
            Event event = eventMapper.toEvent(eventDto, category);
            return eventMapper.toDto(editEvent(eventId, event), Long.valueOf(requestRepository
                    .countParticipationByEventIdAndStatus(eventId, RequestStatusEnum.CONFIRMED)));
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }

    @Transactional
    @Override
    public Event cancelEvent(Long eventId, Long userId) {
        Event event = getEventOrThrow(eventId);
        if (!event.getState().equals(EventStateEnum.PENDING)) {
            log.error("Can not reject Event with status {}.", event.getState());
            throw new ArgumentNotValidException(this.getClass().getName(),
                    "Event event must have status PENDING",
                    String.format("Event id%d has status %s.", eventId, event.getState()));
        } else if (!event.getInitiator().getId().equals(userId)) {
            throw new AccessForbiddenException(this.getClass().getName(),
                    "Only initiator of event can change it",
                    String.format("Event id%d has initiator id %d.", eventId, event.getInitiator().getId()));
        }
        event.setState(EventStateEnum.CANCELED);
        Event savedEvent = eventRepository.save(event);
        log.info("Отменено событие id{} пользователя id{}.", eventId, userId);
        return savedEvent;
    }

    @Override
    public EventFullDto cancelEvent(Long eventId, Long userId, String role) throws AccessForbiddenException {
        if (RoleEnum.of(role) == RoleEnum.USER) {
            return eventMapper.toDto(cancelEvent(eventId, userId), Long.valueOf(requestRepository
                    .countParticipationByEventIdAndStatus(eventId, RequestStatusEnum.CONFIRMED)));
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }


    @Override
    public List<Event> findEvent(Long[] userIds, EventStateEnum[] states, Long[] categoryIds, LocalDateTime
            rangeStart, LocalDateTime rangeEnd, Integer from, Integer size, Long userId) {
        return eventCustomRepository.findEvents(userIds, states, categoryIds, rangeStart, rangeEnd, from, size);
    }

    @Override
    public List<Event> findEvent(Long userId, Integer from, Integer size) {
        User user = getUserOrThrow(userId);
        Optional<PageRequest> pageRequest = pageValidated(from, size);
        return pageRequest
                .map(request -> eventRepository.findEventsByInitiator(user, request))
                .orElseGet(() -> eventRepository.findEventsByInitiator(user));
    }

    @Override
    public List<EventFullDto> findEvent(Long[] userIds, String[] states, Long[] categoryIds, String rangeStart,
                                        String rangeEnd, Integer from, Integer size, Long userId, String role)
            throws AccessForbiddenException {
        if (RoleEnum.of(role) == RoleEnum.ADMINISTRATOR) {
            return findEvent(userIds, states != null ? EventStateEnum.of(states) : null, categoryIds,
                    rangeStart != null ? LocalDateTime.parse(rangeStart, DATE_TIME_FORMATTER) : null,
                    rangeEnd != null ? LocalDateTime.parse(rangeEnd, DATE_TIME_FORMATTER) : null, from, size, userId)
                    .stream()
                    .map(e -> eventMapper.toDto(e, Long.valueOf(requestRepository
                            .countParticipationByEventIdAndStatus(e.getId(), RequestStatusEnum.CONFIRMED))))
                    .collect(Collectors.toList());
        } else if (RoleEnum.of(role) == RoleEnum.USER) {
            return findEvent(userId, from, size)
                    .stream()
                    .map(e -> eventMapper.toDto(e, Long.valueOf(requestRepository
                            .countParticipationByEventIdAndStatus(e.getId(), RequestStatusEnum.CONFIRMED))))
                    .collect(Collectors.toList());
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }

    @Transactional
    @Override
    public Event publishEvent(Long eventId) {
        Event event = getEventOrThrow(eventId);
        if (event.getEventDate().isBefore(LocalDateTime.now().minusHours(1))) {
            throw new BadRequestException(this.getClass().getName(),
                    "Event must start min after one hour of now",
                    String.format("Event id%d has event date %s.", eventId, event.getEventDate()));
        }
        if (!event.getState().equals(EventStateEnum.PENDING)) {
            throw new BadRequestException(this.getClass().getName(),
                    "State of event must be PENDING",
                    String.format("Event id%d has state %s.", eventId, event.getState()));
        }
        event.setState(EventStateEnum.PUBLISHED);
        return eventRepository.save(event);
    }

    @Override
    public EventFullDto publishEvent(Long eventId, String role) throws AccessForbiddenException {
        if (RoleEnum.of(role) == RoleEnum.ADMINISTRATOR) {
            return eventMapper.toDto(publishEvent(eventId), Long.valueOf(requestRepository
                    .countParticipationByEventIdAndStatus(eventId, RequestStatusEnum.CONFIRMED)));
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }

    @Transactional
    @Override
    public Event rejectEvent(Long eventId) {
        Event event = getEventOrThrow(eventId);
        if (event.getState().equals(EventStateEnum.REJECT) ||
                event.getState().equals(EventStateEnum.PUBLISHED)) {
            log.error("Can not reject Event with status {}.", event.getState());
            throw new ArgumentNotValidException(this.getClass().getName(),
                    "Event should not be not valid status PENDING",
                    String.format("Event id%d has status %s.", eventId, event.getState()));
        }
        event.setState(EventStateEnum.CANCELED);
        eventRepository.save(event);
        log.info("Отклонено событие id{}.", eventId);
        return event;
    }

    @Override
    public EventFullDto rejectEvent(Long eventId, String role) throws AccessForbiddenException {
        if (RoleEnum.of(role) == RoleEnum.ADMINISTRATOR) {
            return eventMapper.toDto(rejectEvent(eventId), Long.valueOf(requestRepository
                    .countParticipationByEventIdAndStatus(eventId, RequestStatusEnum.CONFIRMED)));
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }

    private Event getEventOrThrow(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(List.of(new Error(this.getClass().getName(),
                        String.format("Event %d not found.", eventId))),
                        "Событие с заданным индексом отсутствует.",
                        String.format("Event %d not found.", eventId)));
    }

    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(List.of(new Error(this.getClass().getName(),
                        String.format("User %d not found.", userId))),
                        "Пользователь с заданным индексом отсутствует.",
                        String.format("User %d not found.", userId)));
    }

    private void incrementViews(Event event) {
        long views = event.getViews() + 1;
        event.setViews(views);
    }
}
