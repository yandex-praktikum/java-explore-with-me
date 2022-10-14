package ru.practikum.explorewithme.p_admin.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practikum.explorewithme.EventsSort;
import ru.practikum.explorewithme.dto.EventFullDto;
import ru.practikum.explorewithme.dto.in.AdminUpdateEventRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(path = "/admin/events")
public class AdminEventController {

    private final AdminEventService service;

    @GetMapping
    public List<EventFullDto> getEvents(@RequestParam Long[] users,
                                        @RequestParam String[] states,
                                        @RequestParam Long[] categories,
                                        @RequestParam String rangeStart,
                                        @RequestParam String rangeEnd,
                                        @RequestParam boolean onlyAvailable,
                                        @RequestParam EventsSort sort,
                                        Integer from,
                                        Integer size) {
        log.info("SERVER GET events");
        return service.getEvents(users, states, categories, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size);
    }

    @PutMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable long eventId,
                             @RequestBody AdminUpdateEventRequest dto) {
        return service.updateEvent(eventId, dto);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable long eventId) {
        return service.publishEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable long eventId) {
        return service.rejectEvent(eventId);
    }
}
