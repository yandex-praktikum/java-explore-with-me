package ru.practicum.stats.client;

import org.springframework.web.client.RestTemplate;
import ru.practicum.stats.dto.HitDto;

public class StatsClient {
    private final RestTemplate restTemplate;
    private final String serverUrl = "http://localhost:9090";

    public StatsClient() {
        this.restTemplate = new RestTemplate();
    }

    public void saveHit(HitDto hitDto) {
        restTemplate.postForObject(serverUrl + "/hit", hitDto, Void.class);
    }

    // Метод для получения статистики можно добавить позже
}