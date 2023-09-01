package ru.practicum.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<Hit, Long> {

    @Query(ALL_STATS_QUERY)
    List<StatsDto> getListAllStats(@Param("start") LocalDateTime start,
                                   @Param("end") LocalDateTime end);

    @Query(UNIQUE_STATS_QUERY)
    List<StatsDto> getListOfUniqueStatistics(@Param("start") LocalDateTime start,
                                             @Param("end") LocalDateTime end);

    @Query("select new ru.practicum.stats.StatsDto(Hit.app, Hit.uri, COUNT(Hit.ip)) "
            + "from Hit "
            + "where Hit.timestamp >= :start and Hit.timestamp <= :end and Hit.uri IN :uris "
            + "group by Hit.app, Hit.uri "
            + "order by COUNT(Hit.ip) desc")
    List<StatsDto> getStatisticsByUrlsAndTimes(@Param("start") LocalDateTime start,
                                               @Param("end") LocalDateTime end,
                                               @Param("uris") List<String> uris);

    @Query("select new ru.practicum.stats.StatsDto(Hit.app, Hit.uri, COUNT(distinct Hit.ip)) "
            + "from Hit "
            + "where Hit.timestamp >= :start and Hit.timestamp <= :end and Hit.uri IN :uris "
            + "group by Hit.app, Hit.uri "
            + "order by COUNT(distinct Hit.ip) desc")
    List<StatsDto> getUniqueStatisticsByUrisAndTimes(@Param("start") LocalDateTime start,
                                                     @Param("end") LocalDateTime end,
                                                     @Param("uris") List<String> uris);

    String ALL_STATS_QUERY = "select new ru.practicum.stats.ViewStatsDto(Hit.app, Hit.uri, COUNT(Hit.ip)) "
            + "from Hit "
            + "where Hit.timestamp >= :start and Hit.timestamp <= :end ";

    String UNIQUE_STATS_QUERY = "select new ru.practicum.stats.ViewStatsDto(Hit.app, Hit.uri, COUNT(distinct Hit.ip)) "
            + "from Hit "
            + "where Hit.timestamp >= :start and Hit.timestamp <= :end ";
}
