package ru.explorewithme.admin.compilations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.admin.dto.CompilationDto;
import ru.explorewithme.admin.dto.NewCategoryDto;
import ru.explorewithme.admin.dto.NewCompilationDto;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/compilations")
@Slf4j
@Validated
public class CompilationController {
    private CompilationService compilationService;

    public CompilationController(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @PostMapping
    public CompilationDto addCompilation(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        log.info("Creating new compilation: {}", newCompilationDto);
        return compilationService.addCompilation(newCompilationDto);
    }

    @DeleteMapping("/{complId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable Long complId, @PathVariable Long eventId) {
        log.info("Deleting event with id={} from compilation with id={}", eventId, complId);
        compilationService.deleteEventFromCompilation(complId, eventId);
    }

    @PatchMapping("/{complId}/events/{eventId}")
    public void addEventToCompilation(@PathVariable Long complId, @PathVariable Long eventId) {
        log.info("Adding event with id={} to compilation with id={}", eventId, complId);
        compilationService.addEventToCompilation(complId, eventId);
    }

    @DeleteMapping("/{complId}/pin")
    public void unpinCompilation(@PathVariable Long complId) {
        log.info("Unpinning compilation with id={}", complId);
        compilationService.unpinCompilation(complId);
    }

    @PatchMapping("/{complId}/pin")
    public void pinCompilation(@PathVariable Long complId) {
        log.info("Pinning compilation with id={}", complId);
        compilationService.pinCompilation(complId);
    }

    @DeleteMapping("/{complId}")
    public void deleteCompilation(@PathVariable Long complId) {
        log.info("Deleting compilation with id={}", complId);
        compilationService.deleteCompilation(complId);
    }

}
