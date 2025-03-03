package ru.practicum;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class StatsClient {
    private final RestTemplate restTemplate;

    public StatsClient(String urlForRest) {
        restTemplate = new RestTemplateBuilder()
                .rootUri(urlForRest)
                .build();
    }

    public HitsDto makeHitRequest(HitRequest requestData) {
        return restTemplate.postForObject("/hit", requestData, HitsDto.class);
    }

    public List<StatsDto> getStatistics() {
        // todo
        return null;
    }
}
