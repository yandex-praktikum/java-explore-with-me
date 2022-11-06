package ru.explorewithme.users.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.admin.dto.CategoryDto;
import ru.explorewithme.admin.dto.UserDto;
import ru.explorewithme.users.dto.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/events")
@Slf4j
public class EventController {
    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public EventFullDto addEvent(@PathVariable Long userId, @RequestBody @Valid NewEventDto newEventDto) {
        log.info("User with id={} is creating new event: {}", userId, newEventDto);
        return eventService.addEvent(userId, newEventDto);
    }

    @GetMapping
    List<EventShortDto> getEvents(@PathVariable Long userId,
                                 @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                 @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Getting events of user with id={}, from={}, size={}", userId, from, size);
        return eventService.getEvents(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Getting event with id={} of user with id={}", eventId, userId);
        return eventService.getEvent(userId, eventId);
    }

    @PatchMapping
    public EventFullDto changeEvent(@PathVariable Long userId,
                                    @RequestBody @Valid UpdateEventRequest updateEventRequest) {
        log.info("Changing existing event to {}", updateEventRequest);
        return eventService.changeEvent(userId, updateEventRequest);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto cancelEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Cancel event with id={} of user with id={}", eventId, userId);
        return eventService.cancelEvent(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequests(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Getting requests of event with id={} of user with id={}", eventId, userId);
        return eventService.getRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(@PathVariable Long userId, @PathVariable Long eventId, @PathVariable Long reqId) {
        log.info("Confirming request with id={} of event with id={} of user with id={}", reqId, eventId, userId);
        return eventService.confirmRequest(reqId, eventId, userId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@PathVariable Long userId, @PathVariable Long eventId, @PathVariable Long reqId) {
        log.info("Rejecting request with id={} of event with id={} of user with id={}", reqId, eventId, userId);
        return eventService.rejectRequest(reqId, eventId, userId);
    }

}
