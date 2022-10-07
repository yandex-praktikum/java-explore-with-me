package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.model.ParticipationRequest;

import java.util.List;
import java.util.Optional;

@Component
public interface PartRequestRepository extends JpaRepository<ParticipationRequest, Long> {
    @Query(value = "select count(*) from participation_requests where event_id = ?1 and status = 'CONFIRMED'", nativeQuery = true)
    Integer getConfirmedRequestsByEventId(long eventId);

    Optional<ParticipationRequest> findByIdAndRequestorId(long id, long requestorId);

    List<ParticipationRequest> findAllByRequestorId(long requestorId);

    List<ParticipationRequest> findAllByEventId(long eventId);
}
