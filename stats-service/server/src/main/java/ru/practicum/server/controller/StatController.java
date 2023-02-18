package ru.practicum.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatInfoDto;
import ru.practicum.server.service.StatServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@Slf4j
public class StatController {
    private final StatServiceImpl service;

    @PostMapping("/hit")
    public void addHit(@RequestBody HitDto hitDto) {
        log.info("Выполнение запроса addHit с id = {}", hitDto.getId());
        service.addHitInfo(hitDto);
    }

    @GetMapping("/stats")
    public List<StatInfoDto> getStat(
            @RequestParam(name = "start")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam(name = "end")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(required = false, defaultValue = "") List<String> uris,
            @RequestParam(required = false, defaultValue = "false") boolean unique) {
        log.info("Выполнение запроса getStat");
        return service.getStatInfo(start, end, uris, unique);
    }
}