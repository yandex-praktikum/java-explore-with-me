package ru.explorewithme.users.requests;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.users.dto.EventFullDto;
import ru.explorewithme.users.dto.NewEventDto;
import ru.explorewithme.users.dto.ParticipationRequestDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/requests")
@Slf4j
public class RequestController {
    private RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping
    public ParticipationRequestDto addRequest(@PathVariable Long userId, @RequestParam Long eventId) {
        log.info("User with id={} is creating new request for event with id={}", userId, eventId);
        return requestService.addRequest(userId, eventId);
    }

    @GetMapping
    public List<ParticipationRequestDto> getAllRequestOfUser(@PathVariable Long userId) {
        log.info("Getting all requests of user with id={}", userId);
        return requestService.getAllRequestOfUser(userId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequestOfUser(@PathVariable Long userId, @PathVariable Long requestId) {
        log.info("Cancelling request with id={} of user with id={}", requestId, userId);
        return requestService.cancelRequestOfUser(userId, requestId);
    }
}
