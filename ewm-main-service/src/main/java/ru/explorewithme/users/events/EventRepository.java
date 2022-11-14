package ru.explorewithme.users.events;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import ru.explorewithme.users.model.Event;

import java.util.List;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {
    Page<Event> findByInitiator_Id(Long userId, Pageable pageable);

    Event findByInitiator_IdAndId(Long userId, Long eventId);

    @Query("SELECT e FROM Event e WHERE e.id IN :ids")
    List<Event> getEventsAtIds(@Param("ids") Set<Long> ids);
}
