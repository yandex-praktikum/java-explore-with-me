package ru.practicum.explore_with_me.ewm_stats_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.ewm_stats_service.dto.EndpointHitDto;
import ru.practicum.explore_with_me.ewm_stats_service.dto.ViewStatsDto;
import ru.practicum.explore_with_me.ewm_stats_service.service.HitService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HitController {
    private final HitService hitService;

    @GetMapping("/stats")
    public List<ViewStatsDto> getStats(@RequestParam String start,
                                       @RequestParam String end,
                                       @RequestParam(required = false) String[] uris,
                                       @RequestParam(defaultValue = "false") Boolean uniq) {
        log.info("get statistic for uris {}", (Object) uris);
        return hitService.getStats(start, end, uris, uniq);
    }

    @PostMapping("/hit")
    public void saveHit(@RequestBody EndpointHitDto endpointHit) {
        log.info("save hit for uri {}", endpointHit.getUri());
        hitService.saveHit(endpointHit);
    }
}
