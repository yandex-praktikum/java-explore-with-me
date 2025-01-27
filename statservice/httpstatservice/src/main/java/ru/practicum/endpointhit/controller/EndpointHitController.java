package ru.practicum.endpointhit.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.endpointhit.mappers.EndpointHitMapper;
import ru.practicum.endpointhit.service.EndpointHitService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/hit")
public class EndpointHitController {
    private final EndpointHitService endpointHitService;

    @Autowired
    public EndpointHitController(EndpointHitService endpointHitService) {
        this.endpointHitService = endpointHitService;
    }

    @GetMapping
    public List<EndpointHitDto> getAll() {
        log.info("Get all EndpointHits");
        return endpointHitService.getAll().stream()
                .map(EndpointHitMapper::toEndpointHitDto)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHitDto create(@RequestBody EndpointHitDto endpointHitDto) {
        log.info("Creating EndpointHit");
        return EndpointHitMapper.toEndpointHitDto(endpointHitService.create(EndpointHitMapper.toEndpointHit(endpointHitDto)));
    }
}
