package ru.practicum.hits;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.HitRequest;
import ru.practicum.HitsDto;

@Validated
@RestController
@RequestMapping("/hit")
@RequiredArgsConstructor
public class HitController {

    private final HitService service;
    private final HitMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HitsDto createHit(@Valid @RequestBody HitRequest request) {
        Hit hit = service.save(request);
        return mapper.toDto(hit);
    }
}
