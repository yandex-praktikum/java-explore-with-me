package ru.practikum.explorewithme.p_public;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practikum.explorewithme.dto.CategoryDto;
import ru.practikum.explorewithme.dto.CompilationDto;
import ru.practikum.explorewithme.dto.EventFullDto;
import ru.practikum.explorewithme.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class PublicController {

    private final PublicService service;

    @GetMapping("/events")
    public List<EventShortDto> getEvents(@RequestParam String text,
                                         @RequestParam Long[] categories,
                                         @RequestParam Optional<Boolean> paid,
                                         @RequestParam String rangeStart,
                                         @RequestParam String rangeEnd,
                                         @RequestParam Boolean onlyAvailable,
                                         @RequestParam String eventsSort,
                                         @RequestParam Integer from,
                                         @RequestParam Integer size,
                                         HttpServletRequest request) {
        log.info("SERVER GET events with text={}, categories={}, paid={}, rangeStart={}, rangeEnd={}, " +
                        "onlyAvailable={}, sort={}, form={}, size={}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, eventsSort, from, size);
        return service.getEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, eventsSort, from, size, request);
    }

    @GetMapping("/events/{id}")
    public EventFullDto getEvent(@PathVariable long id, HttpServletRequest request) {
        log.info("SERVER GET event with id={}", id);
        return service.getEvent(id, request);
    }

    @GetMapping("/compilations")
    public List<CompilationDto> getCompilations(@RequestParam Optional<Boolean> pinned,
                                          @RequestParam Integer from,
                                          @RequestParam Integer size) {
        log.info("SERVER GET compilations with pinned={}, from={}, size={}", pinned, from, size);
        return service.getCompilations(pinned, from, size);
    }

    @GetMapping("/compilations/{compId}")
    public ResponseEntity<?> getCompilation(@PathVariable long compId) {
        log.info("SERVER GET compilation with id={}", compId);
        return new ResponseEntity<>(service.getCompilation(compId), HttpStatus.OK);
    }

    @GetMapping("/categories")
    public List<CategoryDto> getCategories(@RequestParam Integer from,
                                     @RequestParam Integer size) {
        log.info("SERVER GET categories from={}, size={}", from, size);
        return service.getCategories(from, size);
    }

    @GetMapping("/categories/{catId}")
    public CategoryDto getCategory(@PathVariable long catId) {
        log.info("SERVER GET categories with id={}", catId);
        return service.getCategory(catId);
    }
}
