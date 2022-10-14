package ru.practikum.explorewithme;

import ru.practikum.explorewithme.model.EndpointHit;
import ru.practikum.explorewithme.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {

    EndpointHit saveEndpointHit(EndpointHit endpointHit);

    List<ViewStats> getStat(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique);
}
