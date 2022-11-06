package ru.explorewithme.users.events;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import ru.explorewithme.IdService;
import ru.explorewithme.admin.categories.CategoryRepository;
import ru.explorewithme.admin.dto.AdminUpdateEventRequest;
import ru.explorewithme.admin.dto.GetEventAdminRequest;
import ru.explorewithme.admin.model.Category;
import ru.explorewithme.admin.model.User;;
import ru.explorewithme.admin.users.UserRepository;
import ru.explorewithme.events.dto.GetEventPublicRequest;
import ru.explorewithme.exception.IdException;
import ru.explorewithme.users.dto.*;
import ru.explorewithme.users.model.Event;
import ru.explorewithme.users.model.QEvent;
import ru.explorewithme.users.model.Request;
import ru.explorewithme.users.requests.RequestMapper;
import ru.explorewithme.users.requests.RequestRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventService {
    private EventRepository eventRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;

    private RequestRepository requestRepository;

    private IdService idService;

    public EventService(EventRepository eventRepository,
                        UserRepository userRepository,
                        CategoryRepository categoryRepository,
                        IdService idService,
                        RequestRepository requestRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.idService = idService;
        this.requestRepository = requestRepository;
    }

    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        User user = getUserById(userId);
        Category category = idService.getCategoryById(newEventDto.getCategory());

        Event event = EventMapper.toEvent(user, newEventDto, category);
        eventRepository.save(event);

        EventFullDto eventFullDto = EventMapper.toEventFullDto(event);
        log.info("Added event: {}", eventFullDto);
        return eventFullDto;
    }

    User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IdException("no user with such id"));
    }

    public List<EventShortDto> getEvents(Long userId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return eventRepository.findByInitiator_Id(userId, pageable).stream().map(EventMapper::toEventShortDto).collect(Collectors.toList());
    }

    public EventFullDto changeEvent(Long userId, UpdateEventRequest updateEventRequest) {
        Long eventId = updateEventRequest.getEventId();
        Event event = idService.getEventById(eventId);

        if (updateEventRequest.getAnnotation() != null) event.setAnnotation(updateEventRequest.getAnnotation());
        event.setCategory(idService.getCategoryById(updateEventRequest.getCategory()));
        event.setDescription(updateEventRequest.getDescription());
        event.setEventDate(
                EventMapper.fromStringToLocalDateTime(updateEventRequest.getEventDate())
        );
        event.setPaid(updateEventRequest.getPaid());
        event.setParticipantLimit(updateEventRequest.getParticipantLimit());
        event.setTitle(updateEventRequest.getTitle());

        eventRepository.save(event);
        return EventMapper.toEventFullDto(event);
    }

    public EventFullDto getEvent(Long userId, Long eventId) {
        return EventMapper.toEventFullDto(
                eventRepository.findByInitiator_IdAndId(userId, eventId));
    }

    public EventFullDto cancelEvent(Long userId, Long eventId) {
        Event event = eventRepository.findByInitiator_IdAndId(userId, eventId);
        event.setState("CANCELED");
        return EventMapper.toEventFullDto(event);
    }

    public EventFullDto publishEvent(Long eventId) {
        Event event = idService.getEventById(eventId);

        event.setState("PUBLISHED");
        eventRepository.save(event);
        EventFullDto eventFullDto = EventMapper.toEventFullDto(event);
        log.info("Published event: {}", eventFullDto);
        return eventFullDto;
    }

    public EventFullDto rejectEvent(Long eventId) {
        Event event = idService.getEventById(eventId);

        event.setState("CANCELED");
        eventRepository.save(event);
        EventFullDto eventFullDto = EventMapper.toEventFullDto(event);
        log.info("Rejected event: {}", eventFullDto);
        return eventFullDto;
    }

    public List<EventShortDto> getPublicEvents(GetEventPublicRequest req,
                                               Integer from,
                                               Integer size) {

        QEvent event = QEvent.event;
        List<BooleanExpression> conditions = new ArrayList<>();
        conditions.add(event.description.containsIgnoreCase(req.getText()).or(event.annotation.containsIgnoreCase(req.getText())));
        //conditions.add();
        conditions.add(event.category.id.in(req.getCategories()));
        conditions.add(event.paid.eq(req.getPaid()));
        conditions.add(event.eventDate.between(req.getRangeStart(), req.getRangeEnd()));
        if (req.getOnlyAvailable() == true) conditions.add(event.participantLimit.goe(1));

        BooleanExpression finalCondition = conditions.stream()
                .reduce(BooleanExpression::and)
                .get();

        Iterable<Event> events = eventRepository.findAll(finalCondition);
        List<EventShortDto> eventShortDtos = EventMapper.mapToEventShortDto(events);
        return eventShortDtos;
    }

    public List<EventFullDto> getAdminEvents(GetEventAdminRequest req, Integer from, Integer size) {
        QEvent event = QEvent.event;
        List<BooleanExpression> conditions = new ArrayList<>();
        conditions.add(event.initiator.id.in(req.getUsers()));
        conditions.add(event.state.in(req.getStates()));
        conditions.add(event.category.id.in(req.getCategories()));
        conditions.add(event.eventDate.between(req.getRangeStart(), req.getRangeEnd()));

        BooleanExpression finalCondition = conditions.stream()
                .reduce(BooleanExpression::and)
                .get();

        Iterable<Event> events = eventRepository.findAll(finalCondition);
        List<EventFullDto> eventFullDtos = EventMapper.mapToEventFullDto((events));
        log.info("Getted events by admin: {}", eventFullDtos);
        return eventFullDtos;

    }

    public EventFullDto putAdminEvent(Long eventId, AdminUpdateEventRequest updateEvent) {
        Event event = idService.getEventById(eventId);
        Category category = idService.getCategoryById(updateEvent.getCategory());

        if (updateEvent.getAnnotation() != null) event.setAnnotation(updateEvent.getAnnotation());
        if (updateEvent.getDescription() != null) event.setDescription(updateEvent.getDescription());
        if (updateEvent.getEventDate() != null) event.setEventDate(updateEvent.getEventDate());
        if (updateEvent.getLocation() != null) {
            event.setLocationLat(updateEvent.getLocation().getLat());
            event.setLocationLon(updateEvent.getLocation().getLon());
        }
        if (updateEvent.getPaid() != null) event.setPaid(updateEvent.getPaid());
        if (updateEvent.getParticipantLimit() != null) event.setParticipantLimit(updateEvent.getParticipantLimit());
        if (updateEvent.getRequestModeration() != null) event.setRequestModeration(updateEvent.getRequestModeration());
        if (updateEvent.getTitle() != null) event.setTitle(updateEvent.getTitle());

        eventRepository.save(event);
        EventFullDto eventFullDto = EventMapper.toEventFullDto(event);
        log.info("Putted event by admin: {}", eventFullDto);
        return eventFullDto;
    }

    public EventFullDto getPublicEvent(Long eventId) {
        Event event = idService.getEventById(eventId);
        EventFullDto eventFullDto = EventMapper.toEventFullDto(event);
        log.info("Getted public event={}", eventFullDto);
        return eventFullDto;
    }

    public List<ParticipationRequestDto> getRequests(Long userId, Long eventId) {
        List<Request> requests = requestRepository.findByEvent_Id(eventId);
        List<ParticipationRequestDto> participationRequestDtos = RequestMapper.toParticipationRequestDto(requests);
        return participationRequestDtos;
    }

    public ParticipationRequestDto confirmRequest(Long reqId, Long eventId, Long userId) {
        Request request = idService.getRequestById(reqId);
        request.setStatus("CONFIRMED");
        requestRepository.save(request);
        ParticipationRequestDto participationRequestDto = RequestMapper.toParticipationRequestDto(request);
        return participationRequestDto;
    }

    public ParticipationRequestDto rejectRequest(Long reqId, Long eventId, Long userId) {
        Request request = idService.getRequestById(reqId);
        request.setStatus("REJECTED");
        requestRepository.save(request);
        ParticipationRequestDto participationRequestDto = RequestMapper.toParticipationRequestDto(request);
        return participationRequestDto;
    }
}
