package ru.practicum;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StatsClient {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final RestTemplate restTemplate;

    public StatsClient(String urlForRest) {
        restTemplate = new RestTemplateBuilder()
                .rootUri(urlForRest)
                .build();
    }

    public HitsDto makeHitRequest(HitRequest requestData) {
        return restTemplate.postForObject("/hit", requestData, HitsDto.class);
    }

    public List<StatsDto> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        StringBuilder query = new StringBuilder();
        query
                .append("&")
                .append("start=" + formatter.format(start))
                .append("end=" + formatter.format(end));

        if (uris != null && !uris.isEmpty()) {
            query.append("uris=" + String.join("uris=", uris));
        }
        query.append("unique=" + unique);

        List<StatsDto> result = restTemplate.getForObject(query.toString(), List.class);
        return result;
    }
}
