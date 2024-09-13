package ru.practicum.exploreWithMe.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.exploreWithMe.dto.EndpointHitDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatsClient extends BaseClient {

    public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                .build());
    }

    public ResponseEntity<Object> addHit(EndpointHitDto hitDto) {
        return post("/hit", hitDto);
    }

    public ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .path("/stats")
                .queryParam("start", start.toString())
                .queryParam("end", end.toString())
                .queryParam("unique", unique)
                .queryParam("uris", String.join(",", uris))
                .build();
        String uri = uriComponents.toUriString();
        return get(uri);
    }

}
