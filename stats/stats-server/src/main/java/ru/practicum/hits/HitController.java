package ru.practicum.hit;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<HitsDto> hit(@Valid @RequestBody HitRequest request) {
        Hit hit = service.save(request);
        return new ResponseEntity<>(mapper.toDto(hit), HttpStatus.CREATED);
    }
}
