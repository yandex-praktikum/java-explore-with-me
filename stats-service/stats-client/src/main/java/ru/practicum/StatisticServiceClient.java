package ru.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticServiceClient extends BaseClient {
    @Autowired
    public StatisticServiceClient(@Value("${statistic-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> getStatistic(LocalDateTime start, LocalDateTime end, List<String> uris,
                                               boolean unique) {
        Map<String, Object> parameters = setBaseParameters(start, end);
        parameters.put("uris", uris.toArray());
        parameters.put("unique", unique);
        return get("/stats?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
    }

    public ResponseEntity<Object> getStatistic(LocalDateTime start, LocalDateTime end) {
        Map<String, Object> parameters = setBaseParameters(start, end);
        return get("/stats?start={start}&end={end}", parameters);
    }

    public ResponseEntity<Object> getStatistic(LocalDateTime start, LocalDateTime end, List<String> uris) {
        Map<String, Object> parameters = setBaseParameters(start, end);
        parameters.put("uris", uris.toArray());
        return get("/stats?start={start}&end={end}&uris={uris}", parameters);
    }

    public ResponseEntity<Object> getStatistic(LocalDateTime start, LocalDateTime end, boolean unique) {
        Map<String, Object> parameters = setBaseParameters(start, end);
        parameters.put("unique", unique);
        return get("/stats?start={start}&end={end}&unique={unique}", parameters);
    }

    public ResponseEntity<Object> postStatistic(StatisticEventDto body) {
        return post("/hit", body);
    }

    private Map<String, Object> setBaseParameters(LocalDateTime start, LocalDateTime end) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start",
                start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        parameters.put("end",
                end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return parameters;
    }
}
