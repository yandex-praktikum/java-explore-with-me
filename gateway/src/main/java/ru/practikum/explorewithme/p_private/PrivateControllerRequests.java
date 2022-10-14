package ru.practikum.explorewithme.p_private;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(path = "/users/{userId}/requests")
public class PrivateControllerRequests {

    private final PrivateClientRequests clientRequests;

    @GetMapping
    public ResponseEntity<Object> getRequests(@PathVariable long userId) {
        log.info("GET requests with userId={}", userId);
        return clientRequests.getRequests(userId);
    }

    @PostMapping
    public ResponseEntity<Object> createRequest(@PathVariable long userId,
                                                @RequestParam long eventId) {
        log.info("POST create request with userId={}, eventId={}", userId, eventId);
        return clientRequests.createRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<Object> cancelRequest(@PathVariable long userId,
                                                @PathVariable long requestId ) {
        log.info("PATCH cancel request, userId={}, requestId={}", userId, requestId);
        return clientRequests.cancelRequest(userId, requestId);
    }
}
