package ru.practikum.explorewithme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practikum.explorewithme.model.EndpointHit;
import ru.practikum.explorewithme.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository extends JpaRepository<EndpointHit, Long> {

    @Query("SELECT new ru.practikum.explorewithme.model.ViewStats(eh.app, eh.uri, COUNT(eh.ip) ) " +
            "FROM EndpointHit AS eh " +
            "WHERE eh.uri in ?3 " +
            "AND eh.timestamp between ?1 AND ?2 " +
            "GROUP BY eh.app, eh.ip, eh.uri ")
    List<ViewStats> findAllByUniqueIp(LocalDateTime start, LocalDateTime end, String[] uris);

    @Query("SELECT new ru.practikum.explorewithme.model.ViewStats(eh.app, eh.uri, COUNT(eh.uri) ) " +
            "FROM EndpointHit AS eh " +
            "WHERE eh.uri in ?3 " +
            "AND eh.timestamp between ?1 AND ?2 " +
            "GROUP BY eh.app, eh.uri ")
    List<ViewStats> findAllByUniqueUri(LocalDateTime start, LocalDateTime end, String[] uris);
}
