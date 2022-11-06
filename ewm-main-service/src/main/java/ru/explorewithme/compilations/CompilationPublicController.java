package ru.explorewithme.compilations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.admin.categories.CategoryService;
import ru.explorewithme.admin.compilations.CompilationService;
import ru.explorewithme.admin.dto.CategoryDto;
import ru.explorewithme.admin.dto.CompilationDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/compilations")
@Slf4j
public class CompilationPublicController {
    private CompilationService compilationService;

    public CompilationPublicController(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @GetMapping
    List<CompilationDto> getCompilations(@PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                         @Positive @RequestParam(defaultValue = "10") Integer size,
                                         @RequestParam(defaultValue = "true") Boolean pinned) {
        log.info("Getting compilations, from={}, size={}", from, size);
        return compilationService.getCompilations(from, size, pinned);
    }

    @GetMapping("/{compId}")
    CompilationDto getCompilation(@PathVariable Long compId) {
        log.info("Getting compilation with id={}", compId);
        return compilationService.getCompilation(compId);
    }
}
