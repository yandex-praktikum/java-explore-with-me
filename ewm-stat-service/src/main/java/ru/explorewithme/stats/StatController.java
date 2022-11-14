package ru.explorewithme.stats;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/stats")
@Slf4j
public class StatController {
    private StatService statService;

    public StatController(StatService statService) {
        this.statService = statService;
    }

    @GetMapping
    public List<ViewStats> getStat(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique) {

        return statService.getStat(GetStatRequest.of(start, end, uris, unique));
    }
}
