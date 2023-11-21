package ru.practicum.statisticserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.statdto.ViewStats;
import ru.practicum.statisticserver.model.EndpointHit;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<EndpointHit, Long> {
    @Query("SELECT h.app AS app, h.uri AS uri, count(h.ip) AS hits " +
            "FROM EndpointHit h " +
            "WHERE h.created BETWEEN :start AND :end " +
            "GROUP BY h.app, h.uri, h.ip ORDER BY hits DESC "
    )
    List<ViewStats> countByTimestamp(@Param("start") LocalDateTime start,
                                     @Param("end") LocalDateTime end);

    @Query("SELECT h.app AS app, h.uri AS uri, count(DISTINCT h.ip) AS hits " +
            "FROM EndpointHit h " +
            "WHERE h.created BETWEEN :start AND :end " +
            "GROUP BY h.app, h.uri, h.ip " +
            "ORDER BY hits DESC "
    )
    List<ViewStats> countByTimestampUniqueIp(@Param("start") LocalDateTime start,
                                             @Param("end") LocalDateTime end);

    @Query("SELECT h.app AS app, h.uri AS uri, count(DISTINCT h.ip) AS hits " +
            "FROM EndpointHit h " +
            "WHERE h.created BETWEEN ?1 AND ?2 " +
            "AND h.uri IN (?3) " +
            "GROUP BY h.app, h.uri ORDER BY hits DESC ")
    List<ViewStats> findStatisticWithUnique(@Param("start") LocalDateTime start,
                                            @Param("end") LocalDateTime end,
                                            @Param("uris") List<String> uris);

    @Query("SELECT h.app AS app, h.uri AS uri, count(h.ip) AS hits " +
            "FROM EndpointHit h " +
            "WHERE h.created BETWEEN ?1 AND ?2 " +
            "AND h.uri IN (?3) " +
            "GROUP BY h.app, h.uri ORDER BY hits DESC ")
    List<ViewStats> findStatisticNotUnique(@Param("start") LocalDateTime start,
                                           @Param("end") LocalDateTime end,
                                           @Param("uris") List<String> uris);
}