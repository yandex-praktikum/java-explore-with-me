package ru.practicum;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.InputEventDto;
import ru.practicum.dto.OutputStatsDto;
import ru.practicum.exception.EntityValidationException;
import ru.practicum.service.StatisticsService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService service;

    @PostMapping("/hit")
    public InputEventDto saveEventStats(@RequestBody InputEventDto dto) {
        return service.saveEventStats(dto);
    }

    @GetMapping("/stats")
    public List<OutputStatsDto> getStats(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") boolean unique) {
        if (start.equals(end) || start.isAfter(end)) {
            throw new EntityValidationException("Некорректные начало и конец!");
        }
        return service.getStats(start, end, uris, unique);
    }
}
