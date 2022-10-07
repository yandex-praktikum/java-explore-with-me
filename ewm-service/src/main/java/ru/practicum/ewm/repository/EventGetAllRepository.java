package ru.practicum.ewm.repository;

import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.enums.EventStatus;
import ru.practicum.ewm.model.enums.SortEnum;

import java.time.LocalDateTime;
import java.util.List;

public interface EventGetAllRepository {
    List<Event> getAllEventsForAdm(List<Long> users,
                                   List<EventStatus> states,
                                   List<Long> categories,
                                   LocalDateTime rangeStart,
                                   LocalDateTime rangeEnd,
                                   int from,
                                   int size);

    List<Event> getAllEvents(String text,
                             List<Category> categories,
                             Boolean paid, LocalDateTime rangeStart,
                             LocalDateTime rangeEnd,
                             Boolean onlyAvailable,
                             SortEnum sort,
                             int from,
                             int size);
}
