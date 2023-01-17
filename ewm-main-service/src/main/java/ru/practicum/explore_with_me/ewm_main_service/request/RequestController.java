package ru.practicum.explore_with_me.ewm_main_service.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.ewm_main_service.request.dto.ParticipationRequestDto;
import ru.practicum.explore_with_me.ewm_main_service.request.service.RequestService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class RequestController {
    private static final String HEADER_USER_ID = "X-Explore-With-Me-User-Id";
    private static final String HEADER_USER_ROLE = "X-Explore-With-Me-User-Role";
    private final RequestService requestService;

    @GetMapping
    public List<ParticipationRequestDto> findRequest(@RequestHeader(HEADER_USER_ID) Long userId,
                                                     @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Поиск запросов, UserId={}", userId);
        return requestService.findRequest(userId, role);
    }

    @GetMapping("/event/{eventId}")
    public List<ParticipationRequestDto> findRequests(@PathVariable Long eventId,
                                                      @RequestHeader(HEADER_USER_ID) Long userId,
                                                      @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Поиск запросов, EventId={}, UserId={}", eventId, userId);
        return requestService.findRequests(eventId, userId, role);
    }

    @PostMapping
    public ParticipationRequestDto addRequest(@RequestParam Long eventId,
                                              @RequestHeader(HEADER_USER_ID) Long userId,
                                              @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Создание запроса на участие в событии, EventId={}, UserId={}", eventId, userId);
        return requestService.addRequest(eventId, userId, role);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable Long requestId,
                                                 @RequestHeader(HEADER_USER_ID) Long userId,
                                                 @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Отмена запроса на участие, Id={}, UserId={}", requestId, userId);
        return requestService.cancelRequest(requestId, userId, role);
    }

    @PatchMapping("/{requestId}/event/{eventId}/reject")
    public ParticipationRequestDto rejectRequest(@PathVariable Long eventId,
                                                 @PathVariable Long requestId,
                                                 @RequestHeader(HEADER_USER_ID) Long userId,
                                                 @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Отклонение запроса на участие в событии, Id={}, EventIs={}, UserId={}", requestId, eventId, userId);
        return requestService.rejectRequest(eventId, requestId, userId, role);
    }

    @PatchMapping("/{requestId}/event/{eventId}/confirm")
    public ParticipationRequestDto confirmRequest(@PathVariable Long eventId,
                                                  @PathVariable Long requestId,
                                                  @RequestHeader(HEADER_USER_ID) Long userId,
                                                  @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Подтверждение запроса на пользователя участие в событии, Id={}, EventIs={}, UserId={}", requestId, eventId, userId);
        return requestService.confirmRequest(eventId, requestId, userId, role);
    }
}
