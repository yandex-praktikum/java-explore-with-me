package ru.practicum.explore_with_me.gateway.stats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.explore_with_me.gateway.stats.dto.EndpointHitDto;

@Slf4j
@Controller
@RequiredArgsConstructor
@Validated
class StatsController {
    private final StatsClient statsClient;

    @GetMapping("/stats")
    public ResponseEntity<Object> getStats(@RequestParam String start,
                                           @RequestParam String end,
                                           @RequestParam(required = false) String[] uris,
                                           @RequestParam(defaultValue = "false") Boolean uniq) {
        log.info("Get statistic for uris {}", uris);
        return statsClient.getStats(start, end, uris, uniq);
    }

    @PostMapping("/hit")
    public ResponseEntity<Object> createHit(@RequestBody EndpointHitDto endpointHit) {
        log.info("Save hit for uri {}", endpointHit.getUri());
        return statsClient.createHit(endpointHit);
    }

    @GetMapping("/github/actuator")
    public ResponseEntity<Object> getActuator() {
        return statsClient.actuator();
    }
}
