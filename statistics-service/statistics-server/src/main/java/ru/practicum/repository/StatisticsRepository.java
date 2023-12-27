package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.dto.OutputStatsDto;
import ru.practicum.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<Stats, Long> {
    @Query(" SELECT new ru.practicum.dto.OutputStatsDto(s.app, s.uri, COUNT(DISTINCT s.ip)) " +
            "FROM Stats s " +
            "WHERE s.requestDate BETWEEN ?1 AND ?2 " +
            "AND (s.uri IN (?3) OR (?3) is NULL) " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY COUNT(DISTINCT s.ip) DESC ")
    List<OutputStatsDto> findUniqueStats(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(" SELECT new ru.practicum.dto.OutputStatsDto(s.app, s.uri, COUNT(s.ip)) " +
            "FROM Stats s " +
            "WHERE s.requestDate BETWEEN ?1 AND ?2 " +
            "AND (s.uri IN (?3) OR (?3) is NULL) " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY COUNT(s.ip) DESC ")
    List<OutputStatsDto> findStats(LocalDateTime start, LocalDateTime end, List<String> uris);
}
