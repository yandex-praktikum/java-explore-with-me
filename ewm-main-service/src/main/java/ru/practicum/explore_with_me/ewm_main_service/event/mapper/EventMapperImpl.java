package ru.practicum.explore_with_me.ewm_main_service.event.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.explore_with_me.ewm_main_service.category.mapper.CategoryMapper;
import ru.practicum.explore_with_me.ewm_main_service.category.model.Category;
import ru.practicum.explore_with_me.ewm_main_service.event.dto.*;
import ru.practicum.explore_with_me.ewm_main_service.event.model.Event;
import ru.practicum.explore_with_me.ewm_main_service.event.model.Location;
import ru.practicum.explore_with_me.ewm_main_service.user.dto.UserDto;
import ru.practicum.explore_with_me.ewm_main_service.user.model.User;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class EventMapperImpl implements EventMapper {

    private final CategoryMapper categoryMapper;

    @Override
    public Event toEvent(NewEventDto dto, Category category) {
        if (dto == null && category == null) {
            return null;
        }

        Event event = new Event();

        if (dto != null) {
            event.setAnnotation(dto.getAnnotation());
            event.setCategory(category);
            event.setDescription(dto.getDescription());
            event.setEventDate(LocalDateTime.parse(dto.getEventDate(), DATE_TIME_FORMATTER));
            event.setPaid(dto.getPaid());
            event.setParticipantLimit(dto.getParticipantLimit());
            event.setLatitude(dto.getLocation().getLat());
            event.setLongitude(dto.getLocation().getLon());
            event.setRequestModeration(dto.getRequestModeration());
            event.setTitle(dto.getTitle());
        }

        return event;
    }


    @Override
    public Event toEvent(UpdateEventDto dto, Category category) {
        if (dto == null && category == null) {
            return null;
        }

        Event event = new Event();

        if (dto != null) {
            event.setId(dto.getEventId());
            if (dto.getEventDate() != null) {
                event.setEventDate(LocalDateTime.parse(dto.getEventDate(), DATE_TIME_FORMATTER));
            }
            event.setTitle(dto.getTitle());
            event.setAnnotation(dto.getAnnotation());
            event.setPaid(dto.getPaid());
            event.setDescription(dto.getDescription());
            event.setParticipantLimit(dto.getParticipantLimit());
            if (category != null) {
                event.setCategory(category);
            }
        }

        return event;
    }

    @Override
    public Event toEvent(AdminUpdateEventDto dto, Category category) {
        if (dto == null && category == null) {
            return null;
        }

        Event event = new Event();

        if (dto != null) {
            event.setAnnotation(dto.getAnnotation());
            event.setCategory(category);
            if (dto.getEventDate() != null) {
                event.setEventDate(LocalDateTime.parse(dto.getEventDate(), DATE_TIME_FORMATTER));
            }
            event.setDescription(dto.getDescription());
            if (dto.getLocation() != null) {
                event.setLatitude(dto.getLocation().getLat());
                event.setLongitude(dto.getLocation().getLon());
            }
            event.setPaid(dto.getPaid());
            event.setParticipantLimit(dto.getParticipantLimit());
            event.setRequestModeration(dto.getRequestModeration());
            event.setTitle(dto.getTitle());
        }

        return event;
    }

    @Override
    public EventFullDto toDto(Event event, Long confirmedRequests) {
        if (event == null) {
            return null;
        }

        EventFullDto dto = new EventFullDto();

        dto.setAnnotation(event.getAnnotation());
        dto.setCategory(categoryMapper.toDto(event.getCategory()));
        dto.setConfirmedRequests(confirmedRequests);
        if (event.getCreatedOn() != null) {
            dto.setCreatedOn(DATE_TIME_FORMATTER.format(event.getCreatedOn()));
        }
        dto.setDescription(event.getDescription());
        if (event.getEventDate() != null) {
            dto.setEventDate(DATE_TIME_FORMATTER.format(event.getEventDate()));
        }
        dto.setId(event.getId());
        dto.setInitiator(userToUserDto(event.getInitiator()));
        Location location = new Location();
        location.setLat(event.getLatitude());
        location.setLon(event.getLongitude());
        dto.setLocation(location);
        dto.setPaid(event.getPaid());
        dto.setParticipantLimit(event.getParticipantLimit());
        dto.setPublishedOn(event.getPublishedOn() != null ? DATE_TIME_FORMATTER.format(event.getPublishedOn()) : "");
        dto.setRequestModeration(event.getRequestModeration());
        dto.setState(event.getState().name());
        dto.setTitle(event.getTitle());
        dto.setViews(event.getViews());
        return dto;
    }

    @Override
    public EventShortDto toShortDto(Event event, Long confirmedRequests) {
        if (event == null) {
            return null;
        }

        EventShortDto dto = new EventShortDto();

        dto.setAnnotation(event.getAnnotation());
        dto.setCategory(categoryMapper.toDto(event.getCategory()));
        dto.setConfirmedRequests(confirmedRequests);
        if (event.getEventDate() != null) {
            dto.setEventDate(DATE_TIME_FORMATTER.format(event.getEventDate()));
        }
        dto.setId(event.getId());
        dto.setInitiator(userToUserDto(event.getInitiator()));
        dto.setPaid(event.getPaid());
        dto.setTitle(event.getTitle());
        dto.setViews(event.getViews());

        return dto;
    }

    protected UserDto userToUserDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();

        if (user.getId() != null) {
            userDto.setId(user.getId());
        }
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());

        return userDto;
    }
}
