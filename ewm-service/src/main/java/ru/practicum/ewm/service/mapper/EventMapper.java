package ru.practicum.ewm.service.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.client.EventClient;
import ru.practicum.ewm.dto.AdminUpdateEventRequestDto;
import ru.practicum.ewm.dto.EventFullDto;
import ru.practicum.ewm.dto.EventShortDto;
import ru.practicum.ewm.dto.UpdateEventRequestDto;
import ru.practicum.ewm.dto.newDto.NewEventDto;
import ru.practicum.ewm.exceptions.EwmObjNotFoundException;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.Location;
import ru.practicum.ewm.model.enums.EventStatus;
import ru.practicum.ewm.repository.CategoryRepository;
import ru.practicum.ewm.repository.PartRequestRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventMapper {
    private CategoryRepository catRepository;
    private PartRequestRepository requestRepository;
    private EventClient client;

    public Event fromNewDtoToEvent(NewEventDto dto) {
        Event event = new Event();
        event.setEventDate(dto.getEventDate());
        event.setEventStatus(EventStatus.PENDING);
        event.setAnnotation(dto.getAnnotation());
        event.setTitle(dto.getTitle());
        event.setDescription(event.getDescription());
        event.setCategory(catRepository.findById(dto.getCategory())
                .orElseThrow(() -> new EwmObjNotFoundException(
                        String.format("Category with id=%d was not found", dto.getCategory()))));
        event.setLat(dto.getLocation().getLat());
        event.setLon(dto.getLocation().getLon());
        event.setParticipantLimit(dto.getParticipantLimit());
        event.setPaid(dto.getPaid());
        event.setRequestModeration(dto.getRequestModeration());
        event.setCreated(dto.getCreatedOn());
        return event;
    }

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
        event.setLat(admDto.getLocation().getLat());
        event.setLon(admDto.getLocation().getLon());
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
