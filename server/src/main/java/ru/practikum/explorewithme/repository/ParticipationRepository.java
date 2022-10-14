package ru.practikum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practikum.explorewithme.model.Participation;
import ru.practikum.explorewithme.model.ParticipationStatus;

import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    Optional<Participation> findByRequester_IdAndEvent_Id(long requester_id, long event_id);

    List<Participation> findByStatusAndId(ParticipationStatus status, long id);

    List<Participation> findByRequester_Id(long requester_id);

    List<Participation> findByEvent_idAndEvent_Initiator_Id(long event_id, long event_initiator_id);

    Optional<Participation> findByIdAndEvent_IdAndEvent_Initiator_Id(long id, long event_id, long event_initiator_id);

    Optional<Participation> findByIdAndRequester_id(long id, long requester_id);
}
