package ru.explorewithme.admin.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.admin.dto.AdminUpdateEventRequest;
import ru.explorewithme.admin.dto.GetEventAdminRequest;
import ru.explorewithme.users.dto.EventFullDto;
import ru.explorewithme.users.events.EventRepository;
import ru.explorewithme.users.events.EventService;
import ru.explorewithme.users.model.Event;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping(path = "/admin/events")
public class EventAdminController {
    private EventService eventService;

    public EventAdminController(EventService eventService) {
        this.eventService = eventService;
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable Long eventId) {
        log.info("Publushing event with id={}", eventId);
        return eventService.publishEvent(eventId);
    }

    @GetMapping
    public List<EventFullDto> getAdminEvents(@RequestParam Set<Long> users,
                                             @RequestParam Set<String> states,
                                             @RequestParam Set<Long> categories,
                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                             @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                             @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Getting events by admin");
        return eventService.getAdminEvents(GetEventAdminRequest.of(users, states, categories, rangeStart, rangeEnd), from, size);
    }

    @PutMapping("/{eventId}")
    public EventFullDto putAdminEvent(@PathVariable Long eventId, @RequestBody AdminUpdateEventRequest event) {
        log.info("Putting new event with id={} by admin: {}", eventId, event);
        return eventService.putAdminEvent(eventId, event);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable Long eventId) {
        log.info("Rejecting event with id={}", eventId);
        return eventService.rejectEvent(eventId);
    }
}
