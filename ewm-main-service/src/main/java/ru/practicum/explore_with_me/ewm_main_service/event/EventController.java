package ru.practicum.explore_with_me.ewm_main_service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.ewm_main_service.event.dto.*;
import ru.practicum.explore_with_me.ewm_main_service.event.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
public class EventController {
    private static final String HEADER_USER_ID = "X-Explore-With-Me-User-Id";
    private static final String HEADER_USER_ROLE = "X-Explore-With-Me-User-Role";

    private final EventService eventService;


    @GetMapping("/search")
    public List<EventShortDto> searchEvents(@RequestParam(required = false) String text,
                                            @RequestParam(required = false) Long[] categories,
                                            @RequestParam(required = false) Boolean paid,
                                            @RequestParam(required = false) String rangeStart,
                                            @RequestParam(required = false) String rangeEnd,
                                            @RequestParam(required = false) Boolean onlyAvailable,
                                            @RequestParam(required = false) String sort,
                                            @RequestParam(required = false, defaultValue = "0") int from,
                                            @RequestParam(required = false, defaultValue = "10") int size,
                                            @NotNull @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Найти событие по параметрам, text={}, categories={}, paid={}, rangeStart={}, rangeEnd={}, onlyAvailable={}, " +
                        "sort={}, from={}, size={}", text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort,
                from, size);
        return eventService.searchEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort,
                from, size, role);
    }

    @GetMapping
    public List<EventFullDto> findEvent(@RequestParam(required = false) Long[] users,
                                        @RequestParam(required = false) String[] states,
                                        @RequestParam(required = false) Long[] categories,
                                        @RequestParam(required = false) String rangeStart,
                                        @RequestParam(required = false) String rangeEnd,
                                        @RequestParam(required = false, defaultValue = "0") int from,
                                        @RequestParam(required = false, defaultValue = "10") int size,
                                        @RequestHeader(value = HEADER_USER_ID, required = false) Long userId,
                                        @NotNull @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Найти событие по параметрам, users={}, states={}, categories={}, rangeStart={}, rangeEnd={}, from={}, size={}",
                users, states, categories, rangeStart, rangeEnd, from, size);
        return eventService.findEvent(users, states, categories, rangeStart, rangeEnd, from, size, userId, role);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEvent(@PathVariable Long eventId,
                                 @RequestHeader(value = HEADER_USER_ID, required = false) Long userId,
                                 @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Получить событие,Id={}, userId={}", eventId, userId);
        return eventService.getEvent(eventId, userId, role);
    }

    @PostMapping
    public EventFullDto createEvent(@Valid @RequestBody NewEventDto eventDto,
                                    @RequestHeader(HEADER_USER_ID) Long userId,
                                    @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Создать событие, title={}, userId={}", eventDto.getTitle(), userId);
        return eventService.createEvent(userId, eventDto, role);
    }

    @PatchMapping
    public EventFullDto editEvent(@Valid @RequestBody UpdateEventDto eventDto,
                                  @RequestHeader(HEADER_USER_ID) Long userId,
                                  @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Правка события, title={}, userId={}", eventDto.getTitle(), userId);
        return eventService.editEvent(eventDto, userId, role);
    }

    @PutMapping("/{eventId}")
    public EventFullDto editEvent(@PathVariable Long eventId,
                                  @RequestBody(required = false) AdminUpdateEventDto eventDto,
                                  @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Правка события, title={}", eventDto.getTitle());
        return eventService.editEvent(eventDto, eventId, role);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto cancelEvent(@PathVariable Long eventId,
                                    @RequestHeader(HEADER_USER_ID) Long userId,
                                    @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Отмена события, Id={}, userId={}", eventId, userId);
        return eventService.cancelEvent(eventId, userId, role);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable Long eventId,
                                     @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Публикация события, Id={}", eventId);
        return eventService.publishEvent(eventId, role);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable Long eventId,
                                    @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Отклонение события, Id={}", eventId);
        return eventService.rejectEvent(eventId, role);
    }
}
