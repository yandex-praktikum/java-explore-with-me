package ru.practicum.ewm.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.model.Event;

import java.util.List;
import java.util.Optional;

@Component
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByInitiatorId(long initiatorId, Pageable pageable);
    Optional<Event> findByIdAndInitiatorId(long id, long initiatorId);

}
