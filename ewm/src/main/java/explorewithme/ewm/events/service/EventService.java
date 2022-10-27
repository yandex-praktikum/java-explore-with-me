package explorewithme.ewm.events.service;

import explorewithme.ewm.events.admin.AdminUpdateEventRequest;
import explorewithme.ewm.events.admin.UpdateEventRequest;
import explorewithme.ewm.events.dto.*;
import explorewithme.ewm.events.repository.FilterSort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface EventService {


    void checkEventId(long id);

    List<EventShortDto> getEvents(String text, long[] categories, String startStr, String endStr, boolean onlyAvailable,
                                  FilterSort sort, int from, int size);

    EventFullDto getEventById(long id);

    EventShortDto getEventByIdShort(long id);

    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategoryById(int catId);

    List<EventShortDto> getEventsByUser(long userId, int from, int size);

    List<EventFullDto> getEventsForAdmin(long[] users, String[] states, long[] categories, String startStr,
                                         String endStr, int from, int size);

    EventFullDto updateEventAdmin(AdminUpdateEventRequest adminRequest, long eventId);

    EventFullDto publishEvent(long eventId);

    EventFullDto rejectEvent(long eventId);

    CategoryDto updateCategory(CategoryDto categoryDto);

    CategoryDto createCategory(NewCategoryDto categoryDto);

    void deleteCategory(long catId);

    EventFullDto updateEvent(long userId, UpdateEventRequest updateEventRequest);


    EventFullDto getEventsById(long userId, long eventId);

    EventFullDto createEvent(long userId, NewEventDto newEventDto);

    EventFullDto cancelEvent(long userId, long eventId);

    boolean checkOwnership(long userId, long eventId);

    int checkRequestIsAllowed(long userId, long eventId);
}
