package ru.practicum.statsserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statsserver.Service.EndpointService;
import ru.practicum.statsserver.model.EndpointMapper;
import ru.practicum.statsserver.model.dto.EndpointDto;
import ru.practicum.statsserver.model.dto.EndpointDtoOutput;

import javax.validation.constraints.NotBlank;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class Controller {
    public static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final EndpointService endpointService;

    @PostMapping("/hit")
    public EndpointDto create(@RequestBody EndpointDto endpointDto) {
        log.info("/hit\" + endpoint" + endpointDto);
        return EndpointMapper.toEndpointDto(endpointService.create(EndpointMapper.toEndpoint(endpointDto)));
    }
    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping("/stats")
    public List<EndpointDtoOutput> getStats(@NotBlank @RequestParam String start,
                                            @NotBlank @RequestParam String end,
                                            @RequestParam(required = false) List<String> uris,
                                            @RequestParam(defaultValue = "false") Boolean unique) {
        return endpointService.getStats(
                LocalDateTime.parse(URLDecoder.decode(start, StandardCharsets.UTF_8), FORMAT),
                LocalDateTime.parse(URLDecoder.decode(end, StandardCharsets.UTF_8), FORMAT),
                uris,
                unique);
    }
}
