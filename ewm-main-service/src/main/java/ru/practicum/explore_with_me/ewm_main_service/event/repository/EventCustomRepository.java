package ru.practicum.explore_with_me.ewm_main_service.event.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.explore_with_me.ewm_main_service.event.model.Event;
import ru.practicum.explore_with_me.ewm_main_service.event.model.EventStateEnum;
import ru.practicum.explore_with_me.ewm_main_service.event.model.FilterCollection;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventCustomRepository {

    List<Event> searchEvents(FilterCollection filter);

    List<Event> findEvents(Long[] users, EventStateEnum[] states, Long[] categories, LocalDateTime rangeStart,
                           LocalDateTime rangeEnd, int from, int size);
}
