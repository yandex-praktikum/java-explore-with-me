package ru.practicum.explore_with_me.ewm_main_service.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explore_with_me.ewm_main_service.event.model.Event;
import ru.practicum.explore_with_me.ewm_main_service.user.model.User;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findEventsByInitiator(User initiator, Pageable page);

    List<Event> findEventsByInitiator(User initiator);

    @Query(value = "select e " +
            "from Event e " +
            "where e.id in ?1")
    List<Event> findEventsByIds(List<Long> ids);

    @Override
    boolean existsById(Long eventId);
}
