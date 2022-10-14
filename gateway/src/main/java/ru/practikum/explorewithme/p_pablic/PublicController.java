package ru.practikum.explorewithme.p_pablic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practikum.explorewithme.EventsSort;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Optional;

import static ru.practikum.explorewithme.util.ValidDateTime.checkRangeTime;
import static ru.practikum.explorewithme.util.ValidDateTime.isDateTime;


@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
public class PublicController {

    private final PublicClient publicClient;

    @GetMapping("/events")
    public ResponseEntity<Object> getEvents(@RequestParam(required = false) Optional<String> text,
                                            @RequestParam(required = false) Optional<String[]> categories,
                                            @RequestParam(required = false) Optional<Boolean> paid,
                                            @RequestParam(required = false) Optional<String> rangeStart,
                                            @RequestParam(required = false) Optional<String> rangeEnd,
                                            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                            @RequestParam(defaultValue = "EVENT_DATE") String sort,
                                            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                            @Positive @RequestParam(defaultValue = "10") Integer size,
                                            HttpServletRequest oldRequest) {
        EventsSort eventsSort = EventsSort.from(sort)
                .orElseThrow(() -> new IllegalArgumentException("Unknown sort: " + sort));
        isDateTime(rangeStart);
        isDateTime(rangeEnd);
        checkRangeTime(rangeStart, rangeEnd);
        log.info("GET events with text={}, categories={}, paid={}, rangeStart={}, rangeEnd={}, " +
                        "onlyAvailable={}, sort={}, form={}, size={}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, eventsSort, from, size);
        return publicClient.getEvents(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, eventsSort, from, size, oldRequest);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<Object> getEvent(@PathVariable long id, HttpServletRequest oldRequest) {
        log.info("GET event with id={}", id);
        return publicClient.getEvent(id, oldRequest);
    }

    @GetMapping("/compilations")
    public ResponseEntity<Object> getCompilations(@RequestParam(required = false) Optional<Boolean> pinned,
                                                  @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                  @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET compilations with pinned={}, from={}, size={}", pinned, from, size);
        return publicClient.getCompilations(pinned, from, size);
    }

    @GetMapping("/compilations/{compId}")
    public ResponseEntity<Object> getCompilation(@PathVariable long compId) {
        log.info("GET compilation with id={}", compId);
        return publicClient.getCompilation(compId);
    }

    @GetMapping("/categories")
    public ResponseEntity<Object> getCategories(@PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET categories from={}, size={}", from, size);
        return publicClient.getCategories(from, size);
    }

    @GetMapping("/categories/{catId}")
    public ResponseEntity<Object> getCategory(@PathVariable long catId) {
        log.info("GET categories with id={}", catId);
        return publicClient.getCategory(catId);
    }
}
