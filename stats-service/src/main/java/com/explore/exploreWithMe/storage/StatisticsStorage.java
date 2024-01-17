package com.explore.exploreWithMe.storage;


import com.explore.exploreWithMe.model.App;
import com.explore.exploreWithMe.model.Hit;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;



import java.time.LocalDateTime;
import java.util.List;

@Repository
@AllArgsConstructor

public class StatisticsStorage {
    AppRepository appRepository;

    HitRepository hitRepository;

    public void createApp(App app) {
        appRepository.upsertApp(app.getName(), app.getUri());
    }

    public Hit addHit(Hit hit) {return hitRepository.save(hit);
    }

    public List<App> getStatsByUri(List<String> uris, LocalDateTime start, LocalDateTime end, boolean unique) {
        if (unique) {
            return appRepository.getStatsByUriByUniqueIp(uris, start, end);
        } else {
            return appRepository.getStatsByUriByAllIp(uris, start, end);
        }
    }

    public List<App> getAllStats(List<String> uris, LocalDateTime start, LocalDateTime end, boolean unique) {
        List<App> app;
        if (unique) {
            app = appRepository.getStatsAllUriForUniqueIp(start, end);
        } else {
            app = appRepository.getStatsAllUri(start, end);
        }
        return app;
    }
}
