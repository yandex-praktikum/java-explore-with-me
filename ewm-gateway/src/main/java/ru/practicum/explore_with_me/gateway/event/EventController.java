package ru.practicum.explore_with_me.gateway.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.gateway.client.RoleEnum;
import ru.practicum.explore_with_me.gateway.stats.StatsClient;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor

public class EventController {
    private final EventClient eventClient;
    private final StatsClient statsClient;

    @GetMapping
    public ResponseEntity<Object> searchEvents(@RequestParam(required = false) String text,
                                               @RequestParam(required = false) Long[] categories,
                                               @RequestParam(required = false) Boolean paid,
                                               @RequestParam(required = false) String rangeStart,
                                               @RequestParam(required = false) String rangeEnd,
                                               @RequestParam(required = false) Boolean onlyAvailable,
                                               @RequestParam(required = false) String sort,
                                               @RequestParam(defaultValue = "0") int from,
                                               @RequestParam(defaultValue = "10") int size,
                                               HttpServletRequest httpServletRequest) {
        log.info("Поиск событий");
        statsClient.createHit(httpServletRequest);
        return eventClient.searchEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size,
                RoleEnum.PUBLIC);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Object> getEvent(@PathVariable Long eventId,
                                           HttpServletRequest httpServletRequest) {
        log.info("Получение события по Id {}", eventId);
        statsClient.createHit(httpServletRequest);
        return eventClient.getEvent(eventId, RoleEnum.PUBLIC);
    }
}
