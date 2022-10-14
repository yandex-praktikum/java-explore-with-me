package ru.practikum.explorewithme.p_private.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practikum.explorewithme.dto.EventFullDto;
import ru.practikum.explorewithme.dto.EventShortDto;
import ru.practikum.explorewithme.dto.ParticipationRequestDto;
import ru.practikum.explorewithme.dto.in.NewEventDto;
import ru.practikum.explorewithme.dto.in.UpdateEventRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(path = "/users/{userId}/events")
public class PrivateEventController {

    private final PrivateEventService service;

    @GetMapping
    public List<EventShortDto> getEvents(@PathVariable long userId,
                                         @RequestParam Integer from,
                                         @RequestParam Integer size) {
        log.info("SERVER GET events with userId={}, from={}, size={}", userId, from, size);
        return service.getEvents(userId, from, size);
    }

    @PatchMapping
    public EventFullDto updateEvent(@PathVariable long userId,
                                    @RequestBody UpdateEventRequest event) {
        log.info("SERVER PATCH event with userId={}, event={}", userId, event);
        return service.updateEvent(userId, event);
    }

    @PostMapping
    public EventFullDto createEvent(@PathVariable long userId,
                                    @RequestBody NewEventDto event) {
        log.info("SERVER POST event with userId={}, event={}", userId, event);
        return service.createEvent(userId, event);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEvent(@PathVariable long userId,
                                 @PathVariable long eventId) {
        log.info("GET event with userId={}, eventId={}", userId, eventId);
        return service.getEvent(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto cancelEvent(@PathVariable long userId,
                                    @PathVariable long eventId) {
        log.info("PATCH cancel event, userid={}, eventId={}", userId, eventId);
        return service.cancelEvent(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequests(@PathVariable long userId,
                                               @PathVariable long eventId) {
        log.info("GET requests with userId={}, eventId={}", userId, eventId);
        return service.getEventRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(@PathVariable long userId,
                                                 @PathVariable long eventId,
                                                 @PathVariable long reqId) {
        log.info("PATCH confirm request, userId={}, eventId={}, reqId={}", userId, eventId, reqId);
        return service.confirmEventRequest(userId, eventId, reqId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@PathVariable long userId,
                                                @PathVariable long eventId,
                                                @PathVariable long reqId) {
        log.info("PATCH reject request, userId={}, eventId={}, reqId={}", userId, eventId, reqId);
        return service.rejectEventRequest(userId, eventId, reqId);
    }
}
