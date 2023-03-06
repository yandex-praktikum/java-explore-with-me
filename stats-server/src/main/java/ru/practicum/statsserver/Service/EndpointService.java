package ru.practicum.statsserver.Service;

import ru.practicum.statsserver.model.Endpoint;
import ru.practicum.statsserver.model.dto.EndpointDtoOutput;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointService {
    public Endpoint create(Endpoint endpoint);

    List<EndpointDtoOutput> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
