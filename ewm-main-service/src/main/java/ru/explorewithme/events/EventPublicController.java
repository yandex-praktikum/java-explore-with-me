package ru.explorewithme.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.client.BaseClient;
import ru.explorewithme.events.dto.GetEventPublicRequest;
import ru.explorewithme.users.dto.EventFullDto;
import ru.explorewithme.users.dto.EventShortDto;
import ru.explorewithme.users.events.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/events")
public class EventPublicController {
    private EventService eventService;

    private StatClient statClient;

    public EventPublicController(EventService eventService, StatClient statClient) {
        this.eventService = eventService;
        this.statClient = statClient;
    }

    @GetMapping
    public List<EventShortDto> getPublicEvents(@RequestParam String text,
                                               @RequestParam HashSet<Long> categories,
                                               @RequestParam Boolean paid,
                                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                               @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                               @RequestParam String sort,
                                               @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                               @Positive @RequestParam(defaultValue = "10") Integer size) {
        return eventService.getPublicEvents(
                GetEventPublicRequest.of(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort),
                from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getPublicEvent(@PathVariable Long eventId, HttpServletRequest request) {
        log.info("Getting public event with id={}", eventId);

        EndPoint endPoint = EndPoint
                .builder()
                .id(null)
                .app("ewm-main-service")
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(toStringFromDate(LocalDateTime.now()))
                .build();
        System.out.println("!!!!!!!!!!!!!!!!!!!" + endPoint);
        statClient.addEndPoint(9L, endPoint);

        return eventService.getPublicEvent(eventId);
    }

    String toStringFromDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String s = date.format(formatter);
        return s;
    }
}
