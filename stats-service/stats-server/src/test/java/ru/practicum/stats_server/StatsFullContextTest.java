package ru.practicum.stats_server;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.stats_common.model.EndpointHit;
import ru.practicum.stats_common.model.ViewStats;
import ru.practicum.stats_server.controller.StatsController;
import ru.practicum.stats_server.service.StatsService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StatsFullContextTest {
    private final StatsController statsController;
    private final StatsService statsService;

    private final EndpointHit endpointHit = EndpointHit.builder()
            .app("test APP")
            .uri("/test/uri/1")
            .ip("127.0.0.1")
            .timestamp("2020-05-05 10:00:00")
            .build();

    @Test
    public void shouldAddHitAndGetStats() {
        List<ViewStats> statsFromService = statsService.getStats(
                LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2021, 1, 1, 0, 0, 0),
                null,
                false
        );

        assertEquals(0, statsFromService.size());

        statsController.addHit(endpointHit);

        statsFromService = statsService.getStats(
                LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2021, 1, 1, 0, 0, 0),
                null,
                false
        );

        assertEquals(1, statsFromService.size());

        ViewStats statsFromService1 = statsFromService.get(0);

        assertEquals(endpointHit.getApp(), statsFromService1.getApp());
        assertEquals(endpointHit.getUri(), statsFromService1.getUri());
        assertEquals(1, statsFromService1.getHits());
    }
}
