package ru.practicum.ewmservice.service.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.client.EventClient;
import ru.practicum.ewmservice.dto.AdminUpdateEventRequestDto;
import ru.practicum.ewmservice.dto.EventFullDto;
import ru.practicum.ewmservice.dto.EventShortDto;
import ru.practicum.ewmservice.dto.UpdateEventRequestDto;
import ru.practicum.ewmservice.dto.newDto.NewEventDto;
import ru.practicum.ewmservice.exceptions.EwmObjNotFoundException;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.Location;
import ru.practicum.ewmservice.model.enums.EventStatus;
import ru.practicum.ewmservice.repository.CategoryRepository;
import ru.practicum.ewmservice.repository.PartRequestRepository;
import ru.practicum.ewmservice.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventMapper {
    private CategoryRepository catRepository;
    private PartRequestRepository requestRepository;
    private UserRepository userRepository;
    private EventClient client;

    public Event fromNewDtoToEvent(long userId, NewEventDto dto) {
        Event event = new Event();
        event.setEventDate(dto.getEventDate());
        event.setEventStatus(EventStatus.PENDING);
        event.setAnnotation(dto.getAnnotation());
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setCategory(catRepository.findById(dto.getCategory())
                .orElseThrow(() -> new EwmObjNotFoundException(
                        String.format("Category with id=%d was not found", dto.getCategory()))));
        event.setInitiator(userRepository.findById(userId)
                .orElseThrow(() -> new EwmObjNotFoundException(
                        String.format("User with id=%d was not found", userId))));
        event.setLat(dto.getLocation().getLat());
        event.setLon(dto.getLocation().getLon());
        event.setParticipantLimit(dto.getParticipantLimit());
        event.setPaid(dto.getPaid());
        event.setRequestModeration(dto.getRequestModeration());
        event.setCreated(dto.getCreatedOn());
        return event;
    }

    @Transactional
    public EventFullDto toFullDto(Event event) {
        EventFullDto dto = new EventFullDto();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setConfirmedRequests(requestRepository.getConfirmedRequestsByEventId(event.getId()));
        dto.setAnnotation(event.getAnnotation());
        dto.setDescription(event.getDescription());
        dto.setCategory(CategoryMapper.toCategoryDto(event.getCategory()));
        dto.setInitiator(UserMapper.toUserShortDto(event.getInitiator()));
        dto.setLocation(new Location(event.getLat(), event.getLon()));
        dto.setParticipantLimit(event.getParticipantLimit());
        dto.setPaid(event.getPaid());
        dto.setRequestModeration(event.getRequestModeration());
        dto.setState(event.getEventStatus());
        dto.setEventDate(event.getEventDate());
        dto.setPublishedOn(event.getPublishedOn());
        dto.setCreatedOn(event.getCreated());
        dto.setViews((Integer) client.getViews("/events/" + event.getId()));
        return dto;
    }

    @Transactional
    public EventShortDto toShortDto(Event event) {
        EventShortDto dto = new EventShortDto();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setConfirmedRequests(requestRepository.getConfirmedRequestsByEventId(event.getId()));
        dto.setAnnotation(event.getAnnotation());
        dto.setCategory(CategoryMapper.toCategoryDto(event.getCategory()));
        dto.setInitiator(UserMapper.toUserShortDto(event.getInitiator()));
        dto.setPaid(event.getPaid());
        dto.setEventDate(event.getEventDate());
        dto.setViews((Integer) client.getViews("/events/" + event.getId()));
        return dto;
    }

    public Event toEventFromAdmUpdateDto(AdminUpdateEventRequestDto admDto) {
        Event event = new Event();
        event.setAnnotation(admDto.getAnnotation());
        event.setDescription(admDto.getDescription());
        event.setTitle(admDto.getTitle());
        event.setCategory(catRepository.findById(admDto.getCategory())
                .orElseThrow(() -> new EwmObjNotFoundException(
                        String.format("Category with id=%d was not found", admDto.getCategory()))));
        event.setParticipantLimit(admDto.getParticipantLimit());
        event.setEventDate(admDto.getEventDate());

        if (admDto.getLocation() != null) {
            event.setLat(admDto.getLocation().getLat());
            event.setLon(admDto.getLocation().getLon());
        }

        event.setPaid(admDto.getPaid());
        event.setRequestModeration(admDto.getRequestModeration());
        return event;
    }

    public Event toEventFromUpdDto(UpdateEventRequestDto dto) {
        Event event = new Event();
        event.setId(dto.getEventId());
        event.setDescription(dto.getDescription());
        event.setPaid(dto.getPaid());
        event.setTitle(dto.getTitle());
        event.setCategory(catRepository.findById(dto.getCategory())
                .orElseThrow(() -> new EwmObjNotFoundException(
                        String.format("Category with id=%d was not found", dto.getCategory()))));
        event.setParticipantLimit(dto.getParticipantLimit());
        event.setEventDate(dto.getEventDate());

        event.setAnnotation(dto.getAnnotation());
        return event;
    }

    public List<EventShortDto> getEventShortDtoList(List<Event> events) {
        return events.stream().map(this::toShortDto).collect(Collectors.toList());
    }
}
