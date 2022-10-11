package ru.practicum.ewmservice.controller.publicController;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.dto.CompilationDto;
import ru.practicum.ewmservice.service.CompilationService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequestMapping(path = "/compilations")
@AllArgsConstructor
public class CompilationPublicController {
    private CompilationService service;

    @GetMapping
    public Collection<CompilationDto> getAll(@RequestParam(value = "pinned", defaultValue = "false") @NotNull Boolean pinned,
                                             @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                             @RequestParam(defaultValue = "10") @Positive int size) {

        return service.getAll(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto get(@PathVariable @Positive long compId) {
        return service.get(compId);
    }
}
