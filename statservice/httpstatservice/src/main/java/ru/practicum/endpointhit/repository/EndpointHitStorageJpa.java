package ru.practicum.endpointhit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.endpointhit.model.EndpointHit;
import ru.practicum.stats.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EndpointHitStorageJpa extends JpaRepository<EndpointHit, Long> {
    @Query("SELECT new ru.practicum.stats.model.ViewStats(e.app, e.uri, COUNT(e)) " +
            "FROM EndpointHit e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "GROUP BY e.app, e.uri")
    List<ViewStats> getStatsBetweenStartAndEnd(@Param("start") LocalDateTime start,
                                               @Param("end") LocalDateTime end);

    @Query("SELECT new ru.practicum.stats.model.ViewStats(e.app, e.uri, COUNT(e)) " +
            "FROM EndpointHit e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "AND e.uri IN :uris " +
            "GROUP BY e.app, e.uri")
    List<ViewStats> getStatsWithUris(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end,
                                     @Param("uris") List<String> uris);

    @Query("SELECT new ru.practicum.stats.model.ViewStats(e.app, e.uri, COUNT(e)) " +
            "FROM EndpointHit e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "GROUP BY e.ip, e.app, e.uri")
    List<ViewStats> getStatsWithUniqueIp(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT new ru.practicum.stats.model.ViewStats(e.app, e.uri, COUNT(e)) " +
            "FROM EndpointHit e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "AND e.uri IN :uris " +
            "GROUP BY e.ip, e.app, e.uri")
    List<ViewStats> getStatsWithUrisAndUniqueIp(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end,
                                                @Param("uris") List<String> uris);
}
