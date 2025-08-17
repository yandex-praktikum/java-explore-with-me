package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.StatsDto;
import ru.practicum.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<Hit, Integer> {
    @Query("SELECT new ru.practicum.StatsDto(h.app, h.uri, count(h.id)) " +
            "FROM Hit AS h " +
            "WHERE h.uri IN ?1 " +
            "AND h.timestamp BETWEEN ?2 AND ?3 " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY count(h.id) DESC")
    List<StatsDto> findStatsByUriAndStartAndEnd(List<String> uri, LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.StatsDto(h.app, h.uri, count(h.id)) " +
            "FROM Hit AS h " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY count(h.id) DESC")
    List<StatsDto> findStatsStartAndEnd(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.StatsDto(h.app, h.uri, count(DISTINCT h.ip)) " +
            "FROM Hit AS h " +
            "WHERE h.uri IN ?1 " +
            "AND h.timestamp BETWEEN ?2 AND ?3 " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY count(h.id) DESC")
    List<StatsDto> findStatsByUriAndStartAndEndUnique(List<String> uri, LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.StatsDto(h.app, h.uri, count(DISTINCT h.id)) " +
            "FROM Hit AS h " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY count(h.id) DESC")
    List<StatsDto> findStatsStartAndEndUnique(LocalDateTime start, LocalDateTime end);
}