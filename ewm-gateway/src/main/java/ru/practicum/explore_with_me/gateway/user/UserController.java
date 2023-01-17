package ru.practicum.explore_with_me.gateway.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.gateway.client.RoleEnum;
import ru.practicum.explore_with_me.gateway.event.EventClient;
import ru.practicum.explore_with_me.gateway.event.dto.NewEventDto;
import ru.practicum.explore_with_me.gateway.event.dto.UpdateEventRequest;
import ru.practicum.explore_with_me.gateway.request.RequestClient;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private static final String USER_ID_API_PREFIX = "/{userId}";
    private static final String USER_ID_EVENTS_API_PREFIX = USER_ID_API_PREFIX + "/events";
    private static final String USER_ID_EVENTS_ID_API_PREFIX = USER_ID_API_PREFIX + "/events/{eventId}";
    private static final String USER_ID_EVENTS_REQUEST_ID_API_PREFIX = USER_ID_EVENTS_ID_API_PREFIX + "/requests/{reqId}";
    private static final String USER_ID_REQUEST_API_PREFIX = USER_ID_API_PREFIX + "/requests";

    private final EventClient eventClient;
    private final RequestClient requestClient;


    @GetMapping(USER_ID_EVENTS_API_PREFIX)
    public ResponseEntity<Object> findEvents(@PathVariable Long userId,
                                             @RequestParam(defaultValue = "0") int from,
                                             @RequestParam(defaultValue = "10") int size) {
        log.info("Получение события пользователя, userId={}, from={}, size={}", userId, from, size);
        return eventClient.findEvents(userId, from, size, RoleEnum.USER);
    }

    @PostMapping(USER_ID_EVENTS_API_PREFIX)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createEvent(@RequestBody NewEventDto requestDto,
                                              @PathVariable Long userId) {
        log.info("Создание события {} пользователем, userId={}", requestDto, userId);
        return eventClient.createEvent(userId, requestDto, RoleEnum.USER);
    }

    @PatchMapping(USER_ID_EVENTS_API_PREFIX)
    public ResponseEntity<Object> editEvent(@RequestBody UpdateEventRequest requestDto,
                                            @PathVariable Long userId) {
        log.info("Правка события {} пользователем, userId={}", requestDto, userId);
        return eventClient.editEvent(userId, requestDto, RoleEnum.USER);
    }

    @GetMapping(USER_ID_EVENTS_ID_API_PREFIX)
    public ResponseEntity<Object> getEvent(@PathVariable Long userId,
                                           @PathVariable Long eventId) {
        log.info("Получение события пользователя, userId={}, eventId={}", userId, eventId);
        return eventClient.getEvent(eventId, userId, RoleEnum.USER);
    }

    @PatchMapping(USER_ID_EVENTS_ID_API_PREFIX)
    public ResponseEntity<Object> cancelEvent(@PathVariable Long userId,
                                              @PathVariable Long eventId) {
        log.info("Отмена события пользователя, userId={}, eventIs={}", userId, eventId);
        return eventClient.cancelEvent(eventId, userId, RoleEnum.USER);
    }

    @GetMapping(USER_ID_EVENTS_ID_API_PREFIX + "/requests")
    public ResponseEntity<Object> findRequests(@PathVariable Long userId,
                                               @PathVariable Long eventId) {
        log.info("Поиск запроса на участие в событии, Id {}", eventId);
        return requestClient.findRequests(eventId, userId, RoleEnum.USER);
    }

    @PatchMapping(USER_ID_EVENTS_REQUEST_ID_API_PREFIX + "/reject")
    public ResponseEntity<Object> rejectRequest(@PathVariable Long userId,
                                                @PathVariable Long eventId,
                                                @PathVariable Long reqId) {
        log.info("Отклонение запроса на участие {} пользователя, userId={}, eventId={}", reqId, userId, eventId);
        return requestClient.rejectRequest(eventId, reqId, userId, RoleEnum.USER);
    }

    @PatchMapping(USER_ID_EVENTS_REQUEST_ID_API_PREFIX + "/confirm")
    public ResponseEntity<Object> confirmRequest(@PathVariable Long userId,
                                                 @PathVariable Long eventId,
                                                 @PathVariable Long reqId) {
        log.info("Подтверждение запроса на участие {} пользователя, userId={}, eventId={}", reqId, userId, eventId);
        return requestClient.confirmRequest(eventId, reqId, userId, RoleEnum.USER);
    }

    @GetMapping(USER_ID_REQUEST_API_PREFIX)
    public ResponseEntity<Object> findRequests(@PathVariable Long userId) {
        log.info("Поиск запроса пользователя на участие, userId={}", userId);
        return requestClient.findRequests(userId, RoleEnum.USER);
    }

    @PostMapping(USER_ID_REQUEST_API_PREFIX)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addRequest(@PathVariable Long userId,
                                             @RequestParam Long eventId) {
        log.info("Создание запроса пользователя на участие в событии {}, userId={}", eventId, userId);
        return requestClient.addRequest(eventId, userId, RoleEnum.USER);
    }

    @PatchMapping(USER_ID_REQUEST_API_PREFIX + "/{reqId}/cancel")
    public ResponseEntity<Object> cancelRequest(@PathVariable Long userId,
                                                @PathVariable Long reqId) {
        log.info("Отмена запроса пользователя на участие, userId={}, requestId={}", userId, reqId);
        return requestClient.cancelRequest(reqId, userId, RoleEnum.USER);
    }
}
