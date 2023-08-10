package ru.practicum.stats_server;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.stats_common.model.ViewStats;
import ru.practicum.stats_server.model.Stats;
import ru.practicum.stats_server.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StatsRepositoryTest {
    private final StatsRepository statsRepository;

    private final Stats stats1 = Stats.builder()
            .id(1L)
            .app("test APP")
            .ip("127.0.0.1")
            .uri("/test/uri/1")
            .timestamp(LocalDateTime.of(2020, 1, 1, 10, 30, 0))
            .build();

    private final Stats stats2 = Stats.builder()
            .id(2L)
            .app("test APP")
            .ip("127.0.0.1")
            .uri("/test/uri/1")
            .timestamp(LocalDateTime.of(2020, 1, 1, 11, 30, 0))
            .build();

    private final Stats stats3 = Stats.builder()
            .id(3L)
            .app("test APP")
            .ip("127.0.0.1")
            .uri("/test/uri/2")
            .timestamp(LocalDateTime.of(2020, 1, 1, 12, 30, 0))
            .build();

    private final Stats stats4 = Stats.builder()
            .id(4L)
            .app("test APP")
            .ip("127.0.0.127")
            .uri("/test/uri/2")
            .timestamp(LocalDateTime.of(2020, 1, 1, 13, 30, 0))
            .build();

    private final Stats stats5 = Stats.builder()
            .id(5L)
            .app("test APP")
            .ip("127.0.0.1")
            .uri("/test/uri/2")
            .timestamp(LocalDateTime.of(2020, 1, 1, 14, 30, 0))
            .build();

    @BeforeEach
    public void beforeEach() {
        statsRepository.save(stats1);
        statsRepository.save(stats2);
        statsRepository.save(stats3);
        statsRepository.save(stats4);
        statsRepository.save(stats5);
    }

    @Nested
    class GetAllStatsDistinctIp {
        @Test
        public void shouldGetTwo() {
            List<ViewStats> viewStatsFromRepository = statsRepository.getAllStatsDistinctIp(
                    LocalDateTime.of(2020, 1, 1, 10, 30, 0),
                    LocalDateTime.of(2020, 1, 1, 14, 30, 0)
            );

            assertEquals(2, viewStatsFromRepository.size());

            ViewStats viewStatsFromRepository1 = viewStatsFromRepository.get(0);
            ViewStats viewStatsFromRepository2 = viewStatsFromRepository.get(1);

            assertEquals(stats3.getApp(), viewStatsFromRepository1.getApp());
            assertEquals(stats3.getUri(), viewStatsFromRepository1.getUri());
            assertEquals(2, viewStatsFromRepository1.getHits());

            assertEquals(stats1.getApp(), viewStatsFromRepository2.getApp());
            assertEquals(stats1.getUri(), viewStatsFromRepository2.getUri());
            assertEquals(1, viewStatsFromRepository2.getHits());
        }

        @Test
        public void shouldGetOne() {
            List<ViewStats> viewStatsFromRepository = statsRepository.getAllStatsDistinctIp(
                    LocalDateTime.of(2020, 1, 1, 10, 30, 0),
                    LocalDateTime.of(2020, 1, 1, 11, 30, 0)
            );

            assertEquals(1, viewStatsFromRepository.size());

            ViewStats viewStatsFromRepository1 = viewStatsFromRepository.get(0);

            assertEquals(stats1.getApp(), viewStatsFromRepository1.getApp());
            assertEquals(stats1.getUri(), viewStatsFromRepository1.getUri());
            assertEquals(1, viewStatsFromRepository1.getHits());
        }

        @Test
        public void shouldGetEmpty() {
            List<ViewStats> viewStatsFromRepository = statsRepository.getAllStatsDistinctIp(
                    LocalDateTime.of(2030, 1, 1, 10, 30, 0),
                    LocalDateTime.of(2030, 1, 1, 11, 30, 0)
            );

            assertEquals(0, viewStatsFromRepository.size());
        }
    }

    @Nested
    class GetAllStats {
        @Test
        public void shouldGetTwo() {
            List<ViewStats> viewStatsFromRepository = statsRepository.getAllStats(
                    LocalDateTime.of(2020, 1, 1, 10, 30, 0),
                    LocalDateTime.of(2020, 1, 1, 14, 30, 0)
            );

            assertEquals(2, viewStatsFromRepository.size());

            ViewStats viewStatsFromRepository1 = viewStatsFromRepository.get(0);
            ViewStats viewStatsFromRepository2 = viewStatsFromRepository.get(1);

            assertEquals(stats3.getApp(), viewStatsFromRepository1.getApp());
            assertEquals(stats3.getUri(), viewStatsFromRepository1.getUri());
            assertEquals(3, viewStatsFromRepository1.getHits());

            assertEquals(stats1.getApp(), viewStatsFromRepository2.getApp());
            assertEquals(stats1.getUri(), viewStatsFromRepository2.getUri());
            assertEquals(2, viewStatsFromRepository2.getHits());
        }

        @Test
        public void shouldGetOne() {
            List<ViewStats> viewStatsFromRepository = statsRepository.getAllStats(
                    LocalDateTime.of(2020, 1, 1, 10, 30, 0),
                    LocalDateTime.of(2020, 1, 1, 11, 30, 0)
            );

            assertEquals(1, viewStatsFromRepository.size());

            ViewStats viewStatsFromRepository1 = viewStatsFromRepository.get(0);

            assertEquals(stats1.getApp(), viewStatsFromRepository1.getApp());
            assertEquals(stats1.getUri(), viewStatsFromRepository1.getUri());
            assertEquals(2, viewStatsFromRepository1.getHits());
        }

        @Test
        public void shouldGetEmpty() {
            List<ViewStats> viewStatsFromRepository = statsRepository.getAllStats(
                    LocalDateTime.of(2030, 1, 1, 10, 30, 0),
                    LocalDateTime.of(2030, 1, 1, 11, 30, 0)
            );

            assertEquals(0, viewStatsFromRepository.size());
        }
    }

    @Nested
    class GetStatsByUrisDistinctIp {
        @Test
        public void shouldGetTwo() {
            List<ViewStats> viewStatsFromRepository = statsRepository.getStatsByUrisDistinctIp(
                    LocalDateTime.of(2020, 1, 1, 10, 30, 0),
                    LocalDateTime.of(2020, 1, 1, 14, 30, 0),
                    List.of(stats1.getUri(), stats3.getUri())
            );

            assertEquals(2, viewStatsFromRepository.size());

            ViewStats viewStatsFromRepository1 = viewStatsFromRepository.get(0);
            ViewStats viewStatsFromRepository2 = viewStatsFromRepository.get(1);

            assertEquals(stats3.getApp(), viewStatsFromRepository1.getApp());
            assertEquals(stats3.getUri(), viewStatsFromRepository1.getUri());
            assertEquals(2, viewStatsFromRepository1.getHits());

            assertEquals(stats1.getApp(), viewStatsFromRepository2.getApp());
            assertEquals(stats1.getUri(), viewStatsFromRepository2.getUri());
            assertEquals(1, viewStatsFromRepository2.getHits());
        }

        @Test
        public void shouldGetOne() {
            List<ViewStats> viewStatsFromRepository = statsRepository.getStatsByUrisDistinctIp(
                    LocalDateTime.of(2020, 1, 1, 10, 30, 0),
                    LocalDateTime.of(2020, 1, 1, 14, 30, 0),
                    List.of(stats1.getUri())
            );

            assertEquals(1, viewStatsFromRepository.size());

            ViewStats viewStatsFromRepository1 = viewStatsFromRepository.get(0);

            assertEquals(stats1.getApp(), viewStatsFromRepository1.getApp());
            assertEquals(stats1.getUri(), viewStatsFromRepository1.getUri());
            assertEquals(1, viewStatsFromRepository1.getHits());
        }

        @Test
        public void shouldGetEmpty() {
            List<ViewStats> viewStatsFromRepository = statsRepository.getStatsByUrisDistinctIp(
                    LocalDateTime.of(2020, 1, 1, 10, 30, 0),
                    LocalDateTime.of(2020, 1, 1, 14, 30, 0),
                    List.of(" ")
            );

            assertEquals(0, viewStatsFromRepository.size());
        }
    }

    @Nested
    class GetStatsByUris {
        @Test
        public void shouldGetTwo() {
            List<ViewStats> viewStatsFromRepository = statsRepository.getStatsByUris(
                    LocalDateTime.of(2020, 1, 1, 10, 30, 0),
                    LocalDateTime.of(2020, 1, 1, 14, 30, 0),
                    List.of(stats1.getUri(), stats3.getUri())
            );

            assertEquals(2, viewStatsFromRepository.size());

            ViewStats viewStatsFromRepository1 = viewStatsFromRepository.get(0);
            ViewStats viewStatsFromRepository2 = viewStatsFromRepository.get(1);

            assertEquals(stats3.getApp(), viewStatsFromRepository1.getApp());
            assertEquals(stats3.getUri(), viewStatsFromRepository1.getUri());
            assertEquals(3, viewStatsFromRepository1.getHits());

            assertEquals(stats1.getApp(), viewStatsFromRepository2.getApp());
            assertEquals(stats1.getUri(), viewStatsFromRepository2.getUri());
            assertEquals(2, viewStatsFromRepository2.getHits());
        }

        @Test
        public void shouldGetOne() {
            List<ViewStats> viewStatsFromRepository = statsRepository.getStatsByUris(
                    LocalDateTime.of(2020, 1, 1, 10, 30, 0),
                    LocalDateTime.of(2020, 1, 1, 14, 30, 0),
                    List.of(stats1.getUri())
            );

            assertEquals(1, viewStatsFromRepository.size());

            ViewStats viewStatsFromRepository1 = viewStatsFromRepository.get(0);

            assertEquals(stats1.getApp(), viewStatsFromRepository1.getApp());
            assertEquals(stats1.getUri(), viewStatsFromRepository1.getUri());
            assertEquals(2, viewStatsFromRepository1.getHits());
        }

        @Test
        public void shouldGetEmpty() {
            List<ViewStats> viewStatsFromRepository = statsRepository.getStatsByUris(
                    LocalDateTime.of(2020, 1, 1, 10, 30, 0),
                    LocalDateTime.of(2020, 1, 1, 14, 30, 0),
                    List.of(" ")
            );

            assertEquals(0, viewStatsFromRepository.size());
        }
    }
}
