package ru.practicum.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.dto.StatInfoDto;
import ru.practicum.server.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatRepository extends JpaRepository<EndpointHit, Long> {

    @Query(value = "SELECT eh.app as app, eh.uri as uri, count(distinct (eh.ip)) as hits " +
            "FROM EndpointHit eh " +
            "WHERE eh.timestamp between ?1 and ?2 " +
            "GROUP BY eh.app, eh.uri ")
    List<StatInfoDto> getUniqueStat(LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT eh.app as app, eh.uri as uri, count(eh.ip) as hits " +
            "FROM EndpointHit eh " +
            "WHERE eh.timestamp between ?1 and ?2 " +
            "GROUP BY eh.app, eh.uri ")
    List<StatInfoDto> getNoUniqueStat(LocalDateTime start, LocalDateTime end);

}