package ru.repository;

import enw.ViewStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatRepository extends JpaRepository<EndpointHit, Long> {
    @Query("""
            SELECT new enw.ViewStats(eh.app, eh.uri, COUNT(DISTINCT eh.ip))
            FROM EndpointHit eh
            WHERE eh.timestamp BETWEEN ?1 AND ?2 AND (?3 IS NULL OR eh.uri IN ?3)
            GROUP BY eh.app, eh.uri
            ORDER BY COUNT(eh.id) DESC
            """)
    List<ViewStats> findAllUniqueIpAndTimestampBetweenAndUriIn(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("""
            SELECT new enw.ViewStats(eh.app, eh.uri, COUNT(eh.id))
            FROM EndpointHit eh
            WHERE eh.timestamp BETWEEN ?1 AND ?2 AND (?3 IS NULL OR eh.uri IN ?3)
            GROUP BY eh.app, eh.uri
            ORDER BY COUNT(eh.id) DESC
            """)
    List<ViewStats> findAllByAndTimestampBetweenAndUriIn(LocalDateTime start, LocalDateTime end, List<String> uris);
}
