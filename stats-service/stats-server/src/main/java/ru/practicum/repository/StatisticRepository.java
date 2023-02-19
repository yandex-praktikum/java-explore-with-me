package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.StatisticViewDto;
import ru.practicum.model.StatisticEvent;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<StatisticEvent, Long> {
    @Query("SELECT new ru.practicum.StatisticViewDto(s.application, s.uri, COUNT(s.id) AS hits)" +
            " FROM StatisticEvent s " +
            "WHERE s.eventTime > ?1 AND s.eventTime < ?2 GROUP BY s.application, s.uri " +
            "ORDER BY hits DESC")
    List<StatisticViewDto> findByDate(LocalDateTime startTime, LocalDateTime andTime);

    @Query("SELECT new ru.practicum.StatisticViewDto(s.application, s.uri, COUNT(s.id) AS hits) " +
            "FROM StatisticEvent s " +
            "WHERE s.eventTime > ?1 AND s.eventTime < ?2 AND s.uri IN ?3 GROUP BY s.application, s.uri " +
            "ORDER BY hits DESC")
    List<StatisticViewDto> findByDateAndByUri(LocalDateTime startTime, LocalDateTime andTime, List<String> uri);

    @Query("SELECT new ru.practicum.StatisticViewDto(s.application, s.uri, COUNT(DISTINCT s.ip) AS hits) " +
            "FROM StatisticEvent s " +
            "WHERE s.eventTime > ?1 AND s.eventTime < ?2 GROUP BY s.application, s.uri " +
            "ORDER BY hits DESC")
    List<StatisticViewDto> findByDateAndUniqueIp(LocalDateTime startTime, LocalDateTime andTime);

    @Query("SELECT new ru.practicum.StatisticViewDto(s.application, s.uri, COUNT(DISTINCT s.ip) AS hits) " +
            "FROM StatisticEvent s " +
            "WHERE s.eventTime > ?1 AND s.eventTime < ?2 AND s.uri IN ?3 GROUP BY s.application, s.uri " +
            "ORDER BY hits DESC")
    List<StatisticViewDto> findByDateAndByUriAndUniqueIp(LocalDateTime startTime, LocalDateTime andTime,
                                                         List<String> uri);
}
