package ru.practikum.explorewithme.p_admin.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practikum.explorewithme.dto.CompilationDto;
import ru.practikum.explorewithme.dto.in.NewCompilationDto;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(path = "/admin/compilations")
public class AdminCompilationController {

    private final AdminCompilationService service;

    @PostMapping
    public CompilationDto createCompilation(@RequestBody NewCompilationDto dto) {
        log.info("SERVER POST create compilation " + dto);
        return service.createCompilation(dto);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable long compId) {
        log.info("SERVER DELETE compilation with id={}", compId);
        service.deleteCompilation(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable long compId,
                                           @PathVariable long eventId) {
        log.info("SERVER DELETE event from compilations, compId={}, eventId={}", compId, eventId);
        service.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventToCompilation(@PathVariable long compId,
                                      @PathVariable long eventId) {
        log.info("SERVER PATCH add event to compilation, compId={}, eventId={}", compId, eventId);
        service.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unpinToMainPage(@PathVariable long compId) {
        log.info("SERVER DELETE unpin to main page, compId={}", compId);
        service.unpinToMainPage(compId);
    }

    @PatchMapping("/{compId}/pin")
    public void pinToMainPage(@PathVariable long compId) {
        log.info("SERVER PATCH pin to main page, compId={}", compId);
        service.pinToMainPage(compId);
    }
}
