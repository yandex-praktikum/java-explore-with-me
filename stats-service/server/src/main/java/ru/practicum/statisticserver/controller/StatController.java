package ru.practicum.statisticserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.statdto.EndpointHitDto;
import ru.practicum.statdto.ViewStats;
import ru.practicum.statisticserver.service.StatisticService;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping
public class StatController {
    private final StatisticService statisticService;

    @PostMapping("/hit")
    public ResponseEntity<EndpointHitDto> saveStat(@RequestBody @Valid EndpointHitDto endpointHit) {
        log.info("Created request to save information about calling of the endpoint " + endpointHit.getUri());
        endpointHit.setCreated(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(statisticService.addStatisticData(endpointHit));
    }

    @GetMapping("/stats")
    public List<ViewStats> getStat(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                   LocalDateTime start,
                                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                   LocalDateTime end,
                                   @RequestParam(required = false) List<String> uris,
                                   @RequestParam(defaultValue = "false", name = "unique") boolean isUnique) {
        log.info("Created request to get statistic data");
        return statisticService.getStatisticData(start, end, uris, isUnique);
    }
}
