package ru.practikum.explorewithme.p_private.event;

import ru.practikum.explorewithme.dto.EventFullDto;
import ru.practikum.explorewithme.dto.EventShortDto;
import ru.practikum.explorewithme.dto.ParticipationRequestDto;
import ru.practikum.explorewithme.dto.in.NewEventDto;
import ru.practikum.explorewithme.dto.in.UpdateEventRequest;

import java.util.List;

public interface PrivateEventService {

    List<EventShortDto> getEvents(long userId, Integer from, Integer size);

    EventFullDto updateEvent(long userId, UpdateEventRequest event);

    EventFullDto createEvent(long userId, NewEventDto event);

    EventFullDto getEvent(long userId, long eventId);

    EventFullDto cancelEvent(long userId, long eventId);

    List<ParticipationRequestDto> getEventRequests(long userId, long eventId);

    ParticipationRequestDto confirmEventRequest(long userId, long eventId, long reqId);

    ParticipationRequestDto rejectEventRequest(long userId, long eventId, long reqId);
}
