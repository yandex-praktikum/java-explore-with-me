package ru.practicum.explore_with_me.stats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.dto.Stat;
import ru.practicum.explore_with_me.dto.ViewStats;
import ru.practicum.explore_with_me.exception.DateException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class StatsService {
    private final StatsRepository repository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Transactional
    public Stat create(Stat stat) {
        return repository.save(stat);
    }

    @Transactional(readOnly = true)
    public List<ViewStats> getStatistic(String start, String end, List<String> uris, boolean unique) {
        LocalDateTime startDate = LocalDateTime.parse(start, formatter);
        LocalDateTime endDate = LocalDateTime.parse(end, formatter);

        if (startDate.isAfter(endDate)) {
            throw new DateException("Дата начала не может быть позже даты окончания");
        }

        List<Stat> foundStats;
        if (uris.isEmpty()) {
            foundStats = repository.findByTimestampBetweenOrderByAppAscUriAsc(startDate, endDate);
        } else {
            foundStats = repository.findByTimestampBetweenAndUriInOrderByAppAscUriAsc(startDate, endDate, uris);
        }

        List<ViewStats> stats = countHitsInFoundStats(foundStats, unique);
        stats.sort((o1, o2) -> o2.getHits().compareTo(o1.getHits()));
        return stats;
    }

    private List<ViewStats> countHitsInFoundStats(List<Stat> foundStats, boolean unique) {
        List<ViewStats> stats = new ArrayList<>();
        ViewStats viewStats = null;
        String previousApp = "";
        String previousUri = "";
        Set<String> ip = new HashSet<>();

        int hits = 0;
        for (Stat stat : foundStats) {
            if (needAddHits(stat, previousApp, previousUri, unique, ip)) {
                ip.add(stat.getIp());
                hits++;
            } else {
                ip.clear();
                stats = addViewStatsToList(viewStats, stats, hits);
                viewStats = new ViewStats(stat.getApp(), stat.getUri(), 0);
                previousApp = stat.getApp();
                previousUri = stat.getUri();
                ip.add(stat.getIp());
                hits = 1;
            }
        }
        stats = addViewStatsToList(viewStats, stats, hits);
        return stats;
    }

    private List<ViewStats> addViewStatsToList(ViewStats viewStats, List<ViewStats> stats, int hits) {
        if (viewStats != null) {
            viewStats.setHits(hits);
            stats.add(viewStats);
        }
        return stats;
    }

    private boolean needAddHits(Stat stat, String previousApp, String previousUri, boolean unique, Set<String> ip) {
        if (stat.getApp().equals(previousApp) && stat.getUri().equals(previousUri)) {
            return (!ip.contains(stat.getIp()) || !unique);
        }
        return false;
    }
}
