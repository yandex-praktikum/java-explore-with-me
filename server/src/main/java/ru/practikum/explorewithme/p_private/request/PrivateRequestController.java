package ru.practikum.explorewithme.p_private.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practikum.explorewithme.dto.ParticipationRequestDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(path = "/users/{userId}/requests")
public class PrivateRequestController {

    private final PrivateRequestService service;

    @GetMapping
    public List<ParticipationRequestDto> getRequests(@PathVariable long userId) {
        log.info("SERVER GET requests with userId={}", userId);
        return service.getRequests(userId);
    }

    @PostMapping
    public ParticipationRequestDto createRequest(@PathVariable long userId,
                                                 @RequestParam long eventId) {
        log.info("SERVER POST create request with userId={}, eventId={}", userId, eventId);
        return service.createRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable long userId,
                                                 @PathVariable long requestId ) {
        log.info("SERVER PATCH cancel request, userId={}, requestId={}", userId, requestId);
        return service.cancelRequest(userId, requestId);
    }
}
