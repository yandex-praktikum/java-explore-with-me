package ru.practikum.explorewithme.p_private;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practikum.explorewithme.dto.in.NewEventDto;
import ru.practikum.explorewithme.dto.in.UpdateEventRequest;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import static ru.practikum.explorewithme.util.ValidDateTime.checkStartTimeOfEvent;

@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(path = "/users/{userId}/events")
public class PrivateControllerEvents {

    private final PrivateClientEvents clientEvents;

    @GetMapping
    public ResponseEntity<Object> getEvents(@PathVariable long userId,
                                            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                            @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET events with userId={}, from={}, size={}", userId, from, size);
        return clientEvents.getEvents(userId, from, size);
    }

    @PatchMapping
    public ResponseEntity<Object> updateEvent(@PathVariable long userId,
                                              @RequestBody @Valid UpdateEventRequest updateEventRequest) {
        log.info("PATCH event with userId={}, event={}", userId, updateEventRequest);
        checkStartTimeOfEvent(updateEventRequest.getEventDate(), 2);
        return clientEvents.updateEvent(userId, updateEventRequest);
    }

    @PostMapping
    public ResponseEntity<Object> createEvent(@PathVariable long userId,
                                              @RequestBody @Valid NewEventDto newEventDto) {
        log.info("POST event with userId={}, event={}", userId, newEventDto);
        checkStartTimeOfEvent(newEventDto.getEventDate(), 2);
        return clientEvents.createEvent(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Object> getEvent(@PathVariable long userId,
                                           @PathVariable long eventId) {
        log.info("GET event with userId={}, eventId={}", userId, eventId);
        return clientEvents.getEvent(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<Object> cancelEvent(@PathVariable long userId,
                                              @PathVariable long eventId) {
        log.info("PATCH cancel event, userid={}, eventId={}", userId, eventId);
        return clientEvents.cancelEvent(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<Object> getRequests(@PathVariable long userId,
                                              @PathVariable long eventId) {
        log.info("GET requests with userId={}, eventId={}", userId, eventId);
        return clientEvents.getEventRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public ResponseEntity<Object> confirmRequest(@PathVariable long userId,
                                                 @PathVariable long eventId,
                                                 @PathVariable long reqId) {
        log.info("PATCH confirm request, userId={}, eventId={}, reqId={}", userId, eventId, reqId);
        return clientEvents.confirmEventRequest(userId, eventId, reqId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public ResponseEntity<Object> rejectRequest(@PathVariable long userId,
                                                 @PathVariable long eventId,
                                                 @PathVariable long reqId) {
        log.info("PATCH reject request, userId={}, eventId={}, reqId={}", userId, eventId, reqId);
        return clientEvents.rejectEventRequest(userId, eventId, reqId);
    }
}
