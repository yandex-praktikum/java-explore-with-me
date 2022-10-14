package ru.practikum.explorewithme.p_admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practikum.explorewithme.dto.in.NewCompilationDto;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(path = "/admin/compilations")
public class AdminControllerCompilations {

    private final AdminClientCompilations clientCompilations;

    @PostMapping
    public ResponseEntity<Object> createCompilation(@RequestBody @Valid NewCompilationDto dto) {
        log.info("POST create compilation " + dto);
        return clientCompilations.createCompilation(dto);
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<Object> deleteCompilation(@PathVariable long compId) {
        log.info("DELETE compilation with id={}", compId);
        return clientCompilations.deleteCompilation(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public ResponseEntity<Object> deleteEventFromCompilation(@PathVariable long compId,
                                                              @PathVariable long eventId) {
        log.info("DELETE event from compilations, compId={}, eventId={}", compId, eventId);
        return clientCompilations.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public ResponseEntity<Object> addEventToCompilation(@PathVariable long compId,
                                                        @PathVariable long eventId) {
        log.info("PATCH add event to compilation, compId={}, eventId={}", compId, eventId);
        return clientCompilations.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public ResponseEntity<Object> unpinToMainPage(@PathVariable long compId) {
        log.info("DELETE unpin to main page, compId={}", compId);
        return clientCompilations.unpinToMainPage(compId);
    }

    @PatchMapping("/{compId}/pin")
    public ResponseEntity<Object> pinToMainPage(@PathVariable long compId) {
        log.info("PATCH pin to main page, compId={}", compId);
        return clientCompilations.pinToMainPage(compId);
    }

}
