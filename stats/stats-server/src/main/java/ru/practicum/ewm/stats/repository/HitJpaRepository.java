package ru.practicum.ewm.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.stats.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HitJpaRepository extends JpaRepository<Hit, Long> {

    @Query(value = "SELECT h.app, h.uri, COUNT(h.*) " +
            "FROM hits AS h " +
            "WHERE h.timestamp >= ?1  AND h.timestamp <= ?2 AND " +
            "(0 = ?3 OR h.uri in ?4) " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.*) DESC"
            , nativeQuery = true)
    List<Object[]> findStatisticsBetweenStartAndEnd(LocalDateTime start, LocalDateTime end, int isUris, List<String> uris);

    @Query(value = "SELECT v.app, v.uri, COUNT(v.*) " +
            "FROM ( " +
            "SELECT DISTINCT h.ip, h.app, h.uri " +
            "FROM hits AS h " +
            "WHERE timestamp >= ?1 AND timestamp <= ?2 AND " +
            "(0 = ?3 OR h.uri in ?4) " +
            ") AS v " +
            "GROUP BY v.app, v.uri " +
            "ORDER BY COUNT(v.*) DESC"
            , nativeQuery = true)
    List<Object[]> findStatisticsBetweenStartAndEndUniqueIp(LocalDateTime start, LocalDateTime end, int isUris, List<String> uris);


}