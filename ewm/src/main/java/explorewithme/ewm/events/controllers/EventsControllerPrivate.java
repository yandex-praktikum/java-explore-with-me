package explorewithme.ewm.events.controllers;

import explorewithme.ewm.events.admin.UpdateEventRequest;
import explorewithme.ewm.events.dto.EventFullDto;
import explorewithme.ewm.events.dto.EventShortDto;
import explorewithme.ewm.events.dto.NewEventDto;
import explorewithme.ewm.events.service.EventService;
import explorewithme.ewm.requests.dto.ParticipationRequestDto;
import explorewithme.ewm.requests.services.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Validated
public class EventsControllerPrivate {

    private final EventService eventService;
    private final RequestService requestService;


    @GetMapping("/{userId}/events")
    public List<EventShortDto> getEventsByUser(@PathVariable long userId,
                                               @RequestParam(name = "from", defaultValue = "0") int from,
                                               @RequestParam(name = "size", defaultValue = "10") int size)
            throws RuntimeException {

        log.debug("Get request to private controller get events by user "+ userId);
        return eventService.getEventsByUser(userId, from, size);
    }

    @PatchMapping("/{userId}/events")
    public EventFullDto updateEvent(@PathVariable long userId,
                                    @Valid @RequestBody UpdateEventRequest updateEventRequest) throws RuntimeException {
        log.debug("Patch request to private controller to update event by user "+ userId);
        return eventService.updateEvent(userId, updateEventRequest);
    }

    @PostMapping("/{userId}/events")
    public EventFullDto createEvent(@PathVariable long userId,
                                    @Valid @RequestBody NewEventDto newEventDto) throws RuntimeException {
        log.debug("Post request to private controller to create event by user "+ userId);
        return eventService.createEvent(userId, newEventDto);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getEventsByUser(@PathVariable long userId,
                                        @PathVariable long eventId)
            throws RuntimeException {
        log.debug("Get request to private controller to get event " + eventId + " by user "+ userId);
        return eventService.getEventsById(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto cancelEvent(@PathVariable long userId,
                                        @PathVariable long eventId)
            throws RuntimeException {
        log.debug("Patch request to private controller to cancel event " + eventId + " by user" + userId);
        return eventService.cancelEvent(userId, eventId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getRequests(@PathVariable long userId,
                                                     @PathVariable long eventId)
        throws RuntimeException {
        log.debug("Get request to private controller to get event " + eventId + " by user" + userId);
        return requestService.getRequestsByEvent(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(@PathVariable long userId,
                                                  @PathVariable long eventId,
                                                  @PathVariable long reqId)
        throws RuntimeException {
        log.debug("Patch request to private controller to confirm request "  + reqId + " event "
                + eventId + " by user" + userId);
        return requestService.confirmRequest(userId, eventId, reqId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@PathVariable long userId,
                                                  @PathVariable long eventId,
                                                  @PathVariable long reqId)
            throws RuntimeException {
        log.debug("Patch request to private controller to request request " + reqId + " event "
                + eventId + " by user" + userId);
        return requestService.rejectRequest(userId, eventId, reqId);
    }

}
