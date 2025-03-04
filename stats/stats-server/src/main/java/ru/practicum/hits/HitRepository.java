package ru.practicum.hits;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.stats.Stat;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HitRepository extends JpaRepository<Hit, Long> {

    @Query("""
                 SELECT new ru.practicum.stats.Stat(h.uri, h.app, COUNT(h.uri))
                 FROM Hit AS h
                 WHERE h.timestamp BETWEEN :start AND :end
                 GROUP BY h.app, h.uri
                 ORDER BY COUNT(h.uri) DESC
           """)
    List<Stat> getStatsInIntervalGroupedByAppAndUriDesc(LocalDateTime start, LocalDateTime end);

    @Query("""
            SELECT new ru.practicum.stats.Stat(h.uri, h.app, COUNT(h.uri))
            FROM Hit AS h
            WHERE h.uri IN (:uris) AND h.timestamp BETWEEN :start AND :end
            GROUP BY h.app, h.uri
            ORDER BY COUNT(h.uri) DESC
            """)
    List<Stat> getStatsInIntervalAndUrisGroupedByAppAndUriDesc(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("""
            SELECT new ru.practicum.stats.Stat(h.uri, h.app, COUNT(DISTINCT h.ip))
            FROM Hit AS h
            WHERE h.timestamp BETWEEN :start AND :end
            GROUP BY h.app, h.uri
            ORDER BY COUNT(DISTINCT h.ip) DESC""")
    List<Stat> getStatsInIntervalAndAppAndUriWithUniqueIpDesc(LocalDateTime start, LocalDateTime end);

    @Query("""
            SELECT new ru.practicum.stats.Stat(h.uri, h.app, COUNT(DISTINCT h.ip))
            FROM Hit AS h
            WHERE h.uri IN (:uris)
            AND h.timestamp BETWEEN :start AND :end
            GROUP BY h.app, h.uri
            ORDER BY COUNT(DISTINCT h.ip) DESC""")
    List<Stat> getStatsInIntervalAndAppAndUrisWithUniqueIpDesc(LocalDateTime start, LocalDateTime end, List<String> uris);
}
