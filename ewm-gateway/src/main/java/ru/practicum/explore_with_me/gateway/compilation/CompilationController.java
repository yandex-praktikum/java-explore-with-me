package ru.practicum.explore_with_me.gateway.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.gateway.client.RoleEnum;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
@Validated
public class CompilationController {
    private final CompilationClient compilationClient;

    @GetMapping
    public ResponseEntity<Object> findCompilations(@RequestParam Optional<Boolean> pinned,
                                                   @RequestParam(defaultValue = "0") int from,
                                                   @RequestParam(defaultValue = "10") int size) {
        log.info("Поиск подборок (с параметрами), pinned {}, from {}, size {}", pinned, from, size);
        return compilationClient.findCompilations(pinned, RoleEnum.PUBLIC, from, size);
    }

    @GetMapping("/{compId}")
    public ResponseEntity<Object> getCompilation(@PathVariable Long compId) {
        log.info("Получение подборки по Id, compId={}", compId);
        return compilationClient.getCompilation(compId, RoleEnum.PUBLIC);
    }
}
