package ru.practicum.ewm.controller.privateController;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.EventFullDto;
import ru.practicum.ewm.dto.EventShortDto;
import ru.practicum.ewm.dto.ParticipationRequestDto;
import ru.practicum.ewm.dto.UpdateEventRequestDto;
import ru.practicum.ewm.dto.newDto.NewEventDto;
import ru.practicum.ewm.model.enums.RequestStatus;
import ru.practicum.ewm.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/events")
@AllArgsConstructor
public class EventPrivateController {

    private EventService service;

    @GetMapping
    public List<EventShortDto> getAllByUserId(@PathVariable @Positive long userId,
                                              @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                              @RequestParam(defaultValue = "10") @Positive int size) {
        return service.getAllByUserId(userId, from, size);
    }

    @PatchMapping
    public EventFullDto updateEvent(@RequestBody UpdateEventRequestDto updEvent,
                                    @PathVariable @Positive long userId) {
        return service.updateEvent(userId, updEvent);
    }

    @PostMapping
    public EventFullDto createEvent(@PathVariable @Positive long userId,
                                    @RequestBody @Valid NewEventDto newEvent) {
        return service.create(userId, newEvent);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventByIdAndUserId(@PathVariable @Positive long userId,
                                              @PathVariable @Positive long eventId) {
        return service.getEventByIdAndUserId(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto cancelEvent(@PathVariable @Positive long userId,
                                    @PathVariable @Positive long eventId) {
        return service.cancelEvent(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getParticipationRequests(@PathVariable @Positive long userId,
                                                                  @PathVariable @Positive long eventId) {
        return service.getParticipationRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmParticipationRequest(@PathVariable @Positive long userId,
                                                @PathVariable @Positive long eventId,
                                                @PathVariable @Positive long reqId) {
        return service.setStatusParticipationRequest(userId, eventId, reqId, RequestStatus.APPROVED);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectParticipationRequest(@PathVariable @Positive long userId,
                                               @PathVariable @Positive long eventId,
                                               @PathVariable @Positive long reqId) {
        return service.setStatusParticipationRequest(userId, eventId, reqId, RequestStatus.REJECTED);
    }
}
