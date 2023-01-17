package ru.practicum.explore_with_me.ewm_main_service.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.ewm_main_service.compilation.dto.CompilationDto;
import ru.practicum.explore_with_me.ewm_main_service.compilation.dto.NewCompilationDto;
import ru.practicum.explore_with_me.ewm_main_service.compilation.service.CompilationService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
public class CompilationController {

    private static final String HEADER_USER_ROLE = "X-Explore-With-Me-User-Role";
    private final CompilationService compilationService;


    @GetMapping
    public List<CompilationDto> findCompilations(@RequestParam(required = false) Boolean pinned,
                                                 @RequestParam(required = false) Integer from,
                                                 @RequestParam(required = false) Integer size) {
        log.info("Поиск подборок по параметрам, pinned={}, from={}, size={}", pinned, from, size);
        return compilationService.findCompilationsToDto(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilation(@PathVariable Long compId) {
        log.info("Получение подборки, Id={}", compId);
        return compilationService.getCompilationToDto(compId);
    }

    @PostMapping
    public CompilationDto createCompilation(@Valid @RequestBody NewCompilationDto compilationDto,
                                            @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Создание подборки, title={}, pinned={}, Events={}", compilationDto.getTitle(),
                compilationDto.getPinned(), compilationDto.getEvents().toString());
        return compilationService.createCompilation(compilationDto, role);
    }

    @DeleteMapping("/{compId}")
    public void removeEvent(@PathVariable Long compId,
                            @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Удаление подборки, Id={}", compId);
        compilationService.removeCompilation(compId, role);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public CompilationDto addEvent(@PathVariable Long compId,
                                   @PathVariable Long eventId,
                                   @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Добавление события в подборку, Id={}, compId={}", eventId, compId);
        return compilationService.addEvent(compId, eventId, role);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void removeEvent(@PathVariable Long compId,
                            @PathVariable Long eventId,
                            @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Удаление события из подборки, Id={}, compId={}", eventId, compId);
        compilationService.removeEvent(compId, eventId, role);
    }

    @PatchMapping("/{compId}/pin")
    public void pinCompilation(@PathVariable Long compId,
                               @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Закрепление подборки, Id={}", compId);
        compilationService.pinCompilation(compId, role);
    }

    @DeleteMapping("/{compId}/pin")
    public void unpinCompilation(@PathVariable Long compId,
                                 @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Открепление подборки, Id={}", compId);
        compilationService.unpinCompilation(compId, role);
    }
}
