package ru.practicum.explore_with_me.ewm_main_service.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explore_with_me.ewm_main_service.event.model.Event;
import ru.practicum.explore_with_me.ewm_main_service.request.model.Request;
import ru.practicum.explore_with_me.ewm_main_service.request.model.RequestStatusEnum;
import ru.practicum.explore_with_me.ewm_main_service.user.model.User;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> getByRequesterId(Long userId);

    @Query(value = "select r " +
            "from Request r " +
            "left join Event e on r.event = e " +
            "where e.id = ?1 " +
            "and e.initiator.id = ?2")
    List<Request> getRequestsByEventAndInitiator(Long eventId, Long userId);

    Optional<Request> getRequestByEventAndRequester(Event event, User user);

    int countParticipationByEventIdAndStatus(Long eventId, RequestStatusEnum status);
}
