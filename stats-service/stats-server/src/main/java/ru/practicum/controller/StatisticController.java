package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.StatisticEventDto;
import ru.practicum.StatisticViewDto;
import ru.practicum.service.StatisticService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;

    @PostMapping(path = "/hit")
    public StatisticEventDto addStatistic(@RequestBody StatisticEventDto eventDto) {
        return statisticService.addEvent(eventDto);
    }

    @GetMapping(path = "/stats")
    public List<StatisticViewDto> getStatistic(@RequestParam("start")
                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                               LocalDateTime startTime,
                                               @RequestParam("end")
                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                               LocalDateTime endTime,
                                               @RequestParam(value = "uris", required = false)
                                               List<String> uris,
                                               @RequestParam(value = "unique", required = false, defaultValue = "false")
                                               Boolean unique) {
        return statisticService.getEvents(startTime, endTime, uris, unique);
    }
}
