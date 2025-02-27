package client;

import org.springframework.web.client.RestTemplate;
import server.model.Hit;

import java.util.Collections;
import java.util.List;

public class StatsClient {
    private final RestTemplate restTemplate;
    private final String serverUrl = "http://localhost:8080";

    public StatsClient() {
        this.restTemplate = new RestTemplate();
    }

    public void saveHit(Hit hit) {
        restTemplate.postForObject(serverUrl + "/hit", hit, Void.class);
    }

    public List<Object> getStats(String start, String end, List<String> uris, boolean unique) {
        // Логика GET-запроса (добавишь позже)
        return Collections.emptyList();
    }
}