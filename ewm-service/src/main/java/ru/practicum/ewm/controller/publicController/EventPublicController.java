package ru.practicum.ewm.controller.publicController;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.EventFullDto;
import ru.practicum.ewm.dto.EventShortDto;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.enums.SortEnum;
import ru.practicum.ewm.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path = "/events")
@AllArgsConstructor
public class EventPublicController {
    EventService service;

    @GetMapping
    public Collection<EventShortDto> getAll(@RequestParam(value = "text", required = false, defaultValue = "") String text,
                                            @RequestParam(value = "categories", required = false) List<Category> categories,
                                            @RequestParam(value = "paid", required = false) Boolean paid,
                                            @RequestParam(value = "rangeStart", required = false)
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime rangeStart,
                                            @RequestParam(value = "rangeEnd", required = false)
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime rangeEnd,
                                            @RequestParam(value = "onlyAvailable", required = false) Boolean onlyAvailable,
                                            @RequestParam(value = "sort") SortEnum sort,
                                            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                            @RequestParam(defaultValue = "10") @Positive int size) {

        return service.getAll(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDto get(@PathVariable @Positive long eventId,
                            HttpServletRequest httpServletRequest) {
        return service.get(eventId, httpServletRequest);
    }
}
