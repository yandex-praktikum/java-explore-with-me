package ru.practicum.stats.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.stats.mappers.ViewStatsMapper;
import ru.practicum.stats.service.ViewStatsService;
import ru.practicum.utils.TimeEncoder;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/stats")
public class ViewStatsController {
    private final ViewStatsService viewStatsService;

    public ViewStatsController(ViewStatsService viewStatsService) {
        this.viewStatsService = viewStatsService;
    }

    @GetMapping
    public List<ViewStatsDto> get(@RequestParam("start") String start, @RequestParam("end") String end,
                                  @RequestParam(value = "uris", required = false) List<String> uris,
                                  @RequestParam(value = "unique", required = false) Boolean unique) {
        log.info("Get stats with parameters start: {}, end: {}, uri: {}, unique: {}", start, end, uris, unique);
        return viewStatsService.getViewStats(TimeEncoder.decodeToLclDt(start), TimeEncoder.decodeToLclDt(end),
                        uris, unique).stream()
                .map(ViewStatsMapper::toViewstatsDto)
                .toList();
    }
}
