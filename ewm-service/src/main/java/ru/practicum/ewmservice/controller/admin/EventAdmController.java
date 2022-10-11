package ru.practicum.ewmservice.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.dto.AdminUpdateEventRequestDto;
import ru.practicum.ewmservice.dto.EventFullDto;
import ru.practicum.ewmservice.model.enums.EventStatus;
import ru.practicum.ewmservice.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
@AllArgsConstructor
public class EventAdmController {
    private EventService service;

    @GetMapping
    public List<EventFullDto> getAllEventsForAdmin(@RequestParam(value = "users", required = false) List<Long> users,
                                                   @RequestParam(value = "states", required = false) List<EventStatus> states,
                                                   @RequestParam(value = "categories", required = false) List<Long> categories,
                                                   @RequestParam(value = "rangeStart", required = false)
                                                   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                   LocalDateTime rangeStart,
                                                   @RequestParam(value = "rangeEnd", required = false)
                                                   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                   LocalDateTime rangeEnd,
                                                   @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                   @RequestParam(defaultValue = "10") @Positive int size) {
        return service.getAllEventsForAdm(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/{eventId}")
    public EventFullDto admUpdateEvent(@PathVariable @Positive long eventId,
                                       @RequestBody AdminUpdateEventRequestDto updEvent) {
        return service.admUpdateEvent(eventId, updEvent);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable @Positive long eventId) {
        return service.setStatusEvent(eventId, EventStatus.PUBLISHED);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable @Positive long eventId) {
        return service.setStatusEvent(eventId, EventStatus.CANCELED);
    }
}
