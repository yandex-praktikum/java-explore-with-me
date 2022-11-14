package ru.explorewithme.users.requests;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.explorewithme.users.model.Event;
import ru.explorewithme.users.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByEvent_Id(Long eventId);

    List<Request> findByRequester_Id(Long requesterId);
}
