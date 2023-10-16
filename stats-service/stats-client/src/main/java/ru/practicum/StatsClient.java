package ru.practicum;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class StatsClient {
    private final WebClient client;

    public StatsClient(String serverUrl) {
        client = WebClient.create(serverUrl);
    }

    public ResponseEntity<List<ViewStatsDto>> getStats(String start, String end, String[] uris, Boolean unique) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stats")
                        .queryParam("start", start)
                        .queryParam("end", end)
                        .queryParam("uris", uris)
                        .queryParam("unique", unique)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntityList(ViewStatsDto.class)
                .block();
    }

    public void addHit(EndpointHitDto endpointHitDto) {
        client.post()
                .uri("/hit")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(endpointHitDto)
                .retrieve()
                .toBodilessEntity()
                .block();
    }


}