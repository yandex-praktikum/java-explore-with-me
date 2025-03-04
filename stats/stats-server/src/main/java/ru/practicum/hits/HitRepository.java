package ru.practicum.hit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.stats.Stat;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HitRepository extends JpaRepository<Hit, Long> {

    String STATS_QUERY = """
            SELECT new ru.practicum.stats.Stat(h.uri, h.app, COUNT(h.uri))
            FROM Hit AS h
            WHERE h.timestamp BETWEEN :start AND :end
            GROUP BY h.app, h.uri
            ORDER BY COUNT(h.uri) DESC
            """;

    String STATS_BY_URIS_QUERY = """
            SELECT new ru.practicum.stats.Stat(h.uri, h.app, COUNT(h.uri))
            FROM Hit AS h
            WHERE h.uri IN (:uris)
            AND h.timestamp BETWEEN :start AND :end
            GROUP BY h.app, h.uri
            ORDER BY COUNT(h.uri) DESC
            """;

    String STATS_UNIQUE_IP_QUERY = """
            SELECT new ru.practicum.stats.Stat(h.uri, h.app, COUNT(DISTINCT h.ip))
            FROM Hit AS h
            WHERE h.timestamp BETWEEN :start AND :end
            GROUP BY h.app, h.uri
            ORDER BY COUNT(DISTINCT h.ip) DESC
            """;

    String STATS_BY_URIS_UNIQUE_IP_QUERY = """
            SELECT new ru.practicum.stats.Stat(h.uri, h.app, COUNT(DISTINCT h.ip))
            FROM Hit AS h
            WHERE h.uri IN (:uris)
            AND h.timestamp BETWEEN :start AND :end
            GROUP BY h.app, h.uri
            ORDER BY COUNT(DISTINCT h.ip) DESC
            """;

    @Query(STATS_QUERY)
    List<Stat> getStats(LocalDateTime start, LocalDateTime end);

    @Query(STATS_BY_URIS_QUERY)
    List<Stat> getStatsByUris(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(STATS_UNIQUE_IP_QUERY)
    List<Stat> getStatsWithUniqueIp(LocalDateTime start, LocalDateTime end);

    @Query(STATS_BY_URIS_UNIQUE_IP_QUERY)
    List<Stat> getStatsByUrisWithUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);
}
