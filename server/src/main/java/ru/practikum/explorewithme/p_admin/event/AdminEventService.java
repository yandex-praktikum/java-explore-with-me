package ru.practikum.explorewithme.p_admin.event;

import ru.practikum.explorewithme.EventsSort;
import ru.practikum.explorewithme.dto.EventFullDto;
import ru.practikum.explorewithme.dto.in.AdminUpdateEventRequest;

import java.util.List;

public interface AdminEventService {

    List<EventFullDto> getEvents(Long[] users,
                                 String[] states,
                                 Long[] categories,
                                 String rangeStart,
                                 String rangeEnd,
                                 boolean onlyAvailable,
                                 EventsSort sort,
                                 Integer from,
                                 Integer size);

    EventFullDto updateEvent(long eventId, AdminUpdateEventRequest dto);

    EventFullDto publishEvent(long eventId);

    EventFullDto rejectEvent(long eventId);
}
