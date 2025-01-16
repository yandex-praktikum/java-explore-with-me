package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {

    List<EndpointHit> findAllStatsByTimestampBetweenAndUriIn(LocalDateTime to, LocalDateTime from, List<String> uris);

    List<EndpointHit> findAllStatsByTimestampBetween(LocalDateTime to, LocalDateTime from);

    long countByUri(String uri);

}
