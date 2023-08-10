package ru.practicum.stats_server;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.stats_common.CommonUtils;
import ru.practicum.stats_common.model.EndpointHit;
import ru.practicum.stats_common.model.ViewStats;
import ru.practicum.stats_server.mapper.StatsMapperImpl;
import ru.practicum.stats_server.model.Stats;
import ru.practicum.stats_server.repository.StatsRepository;
import ru.practicum.stats_server.service.StatsServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatsServiceImplTest {
    @Mock
    private StatsRepository statsRepository;

    @Mock
    private StatsMapperImpl statsMapper;

    @InjectMocks
    private StatsServiceImpl statsServiceImpl;

    @Captor
    private ArgumentCaptor<Stats> statsArgumentCaptor;

    private final EndpointHit endpointHit = EndpointHit.builder()
            .app("test APP")
            .uri("/test/uri/1")
            .ip("127.0.0.1")
            .timestamp("2022-09-06 11:00:23")
            .build();

    private final ViewStats viewStats1 = ViewStats.builder()
            .app("test APP 1")
            .uri("/test/uri/1")
            .hits(1L)
            .build();

    private final ViewStats viewStats2 = ViewStats.builder()
            .app("test APP 2")
            .uri("/test/uri/2")
            .hits(2L)
            .build();

    private final LocalDateTime start = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
    private final LocalDateTime end = LocalDateTime.of(2035, 1, 1, 0, 0, 0);
    private final List<String> uris = List.of("/test/uri/1", "/test/uri/2");


    @Nested
    class AddHit {
        @Test
        public void shouldAdd() {
            when(statsMapper.toStats(any(), any())).thenCallRealMethod();

            statsServiceImpl.addHit(endpointHit);

            verify(statsMapper, times(1)).toStats(any(), any());
            verify(statsRepository, times(1)).save(statsArgumentCaptor.capture());

            Stats savedStats = statsArgumentCaptor.getValue();

            assertEquals(endpointHit.getApp(), savedStats.getApp());
            assertEquals(endpointHit.getUri(), savedStats.getUri());
            assertEquals(endpointHit.getIp(), savedStats.getIp());
            assertEquals(LocalDateTime.parse(endpointHit.getTimestamp(), CommonUtils.DT_FORMATTER), savedStats.getTimestamp());
        }
    }

    @Nested
    class GetStats {
        @Test
        public void shouldGetAllUniqueIfUriIsNull() {
            when(statsRepository.getAllStatsDistinctIp(start, end)).thenReturn(List.of(viewStats1, viewStats2));

            List<ViewStats> stats = statsServiceImpl.getStats(start, end, null, true);

            verify(statsRepository, times(1)).getAllStatsDistinctIp(start, end);

            assertEquals(2, stats.size());
            assertEquals(viewStats1, stats.get(0));
            assertEquals(viewStats2, stats.get(1));
        }

        @Test
        public void shouldGetAllNoUniqueIfUriIsNull() {
            when(statsRepository.getAllStats(start, end)).thenReturn(List.of(viewStats1, viewStats2));

            List<ViewStats> stats = statsServiceImpl.getStats(start, end, null, false);

            verify(statsRepository, times(1)).getAllStats(start, end);

            assertEquals(2, stats.size());
            assertEquals(viewStats1, stats.get(0));
            assertEquals(viewStats2, stats.get(1));
        }

        @Test
        public void shouldGetAllUniqueIfUriIsEmpty() {
            when(statsRepository.getAllStatsDistinctIp(start, end)).thenReturn(List.of(viewStats1, viewStats2));

            List<ViewStats> stats = statsServiceImpl.getStats(start, end, List.of(), true);

            verify(statsRepository, times(1)).getAllStatsDistinctIp(start, end);

            assertEquals(2, stats.size());
            assertEquals(viewStats1, stats.get(0));
            assertEquals(viewStats2, stats.get(1));
        }

        @Test
        public void shouldGetAllNoUniqueIfUriIsEmpty() {
            when(statsRepository.getAllStats(start, end)).thenReturn(List.of(viewStats1, viewStats2));

            List<ViewStats> stats = statsServiceImpl.getStats(start, end, List.of(), false);

            verify(statsRepository, times(1)).getAllStats(start, end);

            assertEquals(2, stats.size());
            assertEquals(viewStats1, stats.get(0));
            assertEquals(viewStats2, stats.get(1));
        }

        @Test
        public void shouldGetUniqueByUri() {
            when(statsRepository.getStatsByUrisDistinctIp(start, end, uris)).thenReturn(List.of(viewStats1, viewStats2));

            List<ViewStats> stats = statsServiceImpl.getStats(start, end, uris, true);

            verify(statsRepository, times(1)).getStatsByUrisDistinctIp(start, end, uris);

            assertEquals(2, stats.size());
            assertEquals(viewStats1, stats.get(0));
            assertEquals(viewStats2, stats.get(1));
        }

        @Test
        public void shouldGetNoUniqueByUri() {
            when(statsRepository.getStatsByUris(start, end, uris)).thenReturn(List.of(viewStats1, viewStats2));

            List<ViewStats> stats = statsServiceImpl.getStats(start, end, uris, false);

            verify(statsRepository, times(1)).getStatsByUris(start, end, uris);

            assertEquals(2, stats.size());
            assertEquals(viewStats1, stats.get(0));
            assertEquals(viewStats2, stats.get(1));
        }
    }
}
