package ru.practicum.statsserver.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.statsserver.model.Endpoint;
import ru.practicum.statsserver.model.dto.EndpointDtoOutput;


import java.time.LocalDateTime;
import java.util.List;

public interface EndpointRepository extends JpaRepository<Endpoint, Long> {
    @Query("select new ru.practicum.statsServer.model.dto.EndpointDtoOutput(en.app,en.uri,count (en.id)) from Endpoint as en where en.timestamp between :start and :end group by en.app,en.uri")
    List<EndpointDtoOutput> findAllByTimestampBetween(LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.statsServer.model.dto.EndpointDtoOutput(en.app,en.uri,count (en.id)) from Endpoint as en where en.timestamp between :start and :end group by en.app,en.uri, en.id")
    List<EndpointDtoOutput> findDistinctByTimestampBetween(LocalDateTime start, LocalDateTime end);

    @Query("select  new ru.practicum.statsServer.model.dto.EndpointDtoOutput(endpoint.app,endpoint.uri,count (endpoint.id)) from Endpoint as endpoint where endpoint.uri in :uris and endpoint.timestamp between :start and :end group by endpoint.app,endpoint.uri, endpoint.id")
    List<EndpointDtoOutput> findDistinctByTimestampBetweenAndUriIn(LocalDateTime start, LocalDateTime end, List<String> uris);
    @Query("select new ru.practicum.statsServer.model.dto.EndpointDtoOutput(en.app,en.uri,count (en.id)) from Endpoint as en where en.timestamp between :start and :end and en.uri in :uris group by en.app,en.uri")
    List<EndpointDtoOutput> findAllByTimestampBetweenAndUriIn(LocalDateTime start, LocalDateTime end, List<String> uris);
}

