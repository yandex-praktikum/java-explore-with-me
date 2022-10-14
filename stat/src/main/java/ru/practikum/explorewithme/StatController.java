package ru.practikum.explorewithme;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practikum.explorewithme.model.EndpointHit;
import ru.practikum.explorewithme.model.EndpointHitDto;
import ru.practikum.explorewithme.model.ViewStats;


import java.time.LocalDateTime;
import java.util.List;

import static ru.practikum.explorewithme.model.EndpointHitMapper.toModel;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatController {

    private final StatService service;

    @PostMapping("/hit")
    public EndpointHit saveEndpointHit(@RequestBody EndpointHitDto dto) {
        log.info("POST stat " + dto);
        return service.saveEndpointHit(toModel(dto));
    }

    @GetMapping("/stats")
    public List<ViewStats> getStat(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime start,
                                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime end,
                                   @RequestParam(required = false) String[] uris,
                                   @RequestParam(defaultValue = "false") boolean unique) {
        log.info("GET stat ");
        return service.getStat(start, end, uris, unique);
    }
}
