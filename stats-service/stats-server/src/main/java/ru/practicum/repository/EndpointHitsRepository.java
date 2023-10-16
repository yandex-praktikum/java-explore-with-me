package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EndpointHitsRepository extends JpaRepository<EndpointHit, Long> {

    String SELECT_PREFIX = "SELECT h.app as app, h.uri as uri,";
    String GROUP_BY_SUFFIX = "GROUP BY h.app, h.uri";

    @Query(SELECT_PREFIX + " COUNT(DISTINCT h.ip) as hits " +
            "FROM EndpointHit AS h " +
            "WHERE h.created BETWEEN ?1 AND ?2 AND h.uri IN ?3 " +
            GROUP_BY_SUFFIX +
            " ORDER BY COUNT(DISTINCT h.ip) DESC")
    List<ViewStats> getStatsUnique(LocalDateTime start, LocalDateTime end, String[] uris);

    @Query(SELECT_PREFIX + " COUNT(DISTINCT h.ip) as hits " +
            "FROM EndpointHit AS h " +
            "WHERE h.created BETWEEN ?1 AND ?2 " +
            GROUP_BY_SUFFIX +
            " ORDER BY COUNT(DISTINCT h.ip) DESC")
    List<ViewStats> getStatsUnique(LocalDateTime start, LocalDateTime end);

    @Query(SELECT_PREFIX + " COUNT(h.id) as hits " +
            "FROM EndpointHit AS h " +
            "WHERE h.created BETWEEN ?1 AND ?2 AND h.uri IN ?3 " +
            GROUP_BY_SUFFIX +
            " ORDER BY COUNT(h.id) DESC")
    List<ViewStats> getAll(LocalDateTime start, LocalDateTime end, String[] uris);

    @Query(SELECT_PREFIX + " COUNT(h.id) as hits " +
            "FROM EndpointHit AS h " +
            "WHERE h.created BETWEEN ?1 AND ?2 " +
            GROUP_BY_SUFFIX +
            " ORDER BY COUNT(h.id) DESC")
    List<ViewStats> getAll(LocalDateTime start, LocalDateTime end);
}