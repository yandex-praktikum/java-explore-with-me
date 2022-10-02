package ru.practicum.ewm.controller.privateController;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.ParticipationRequestDto;
import ru.practicum.ewm.service.ParticipationRequestService;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(path = "/user/{userId}/requests")
@AllArgsConstructor
public class ParticipationRequestPrivateController {
    ParticipationRequestService service;

    @GetMapping
    public List<ParticipationRequestDto> getParticipationRequestsByUserId(@PathVariable @Positive long userId) {
        return service.getParticipationRequestsByUserId(userId);
    }

    @PostMapping
    public ParticipationRequestDto postPartRequest(@PathVariable @Positive long userId,
                                                   @RequestParam @Positive long eventId) {
        return service.postPartRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelMainPartRequest(@PathVariable @Positive long userId,
                                                         @PathVariable @Positive long requestId) {
        return service.cancelMainPartRequest(userId, requestId);
    }
}
