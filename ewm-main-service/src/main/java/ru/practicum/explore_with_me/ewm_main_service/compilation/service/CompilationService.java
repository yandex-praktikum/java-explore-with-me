package ru.practicum.explore_with_me.ewm_main_service.compilation.service;

import ru.practicum.explore_with_me.ewm_main_service.compilation.dto.CompilationDto;
import ru.practicum.explore_with_me.ewm_main_service.compilation.dto.NewCompilationDto;
import ru.practicum.explore_with_me.ewm_main_service.compilation.model.Compilation;
import ru.practicum.explore_with_me.ewm_main_service.exceptions.AccessForbiddenException;

import java.util.List;

public interface CompilationService {

    List<Compilation> findCompilations(Boolean pinned, Integer from, Integer size);

    List<CompilationDto> findCompilationsToDto(Boolean pinned, Integer from, Integer size);

    Compilation getCompilation(Long compId);

    CompilationDto getCompilationToDto(Long compId);

    Compilation createCompilation(Compilation compilation);

    CompilationDto createCompilation(NewCompilationDto compilationDto, String role) throws AccessForbiddenException;

    void removeCompilation(Long compId, String role) throws AccessForbiddenException;

    Compilation addEvent(Long compId, Long eventId);

    CompilationDto addEvent(Long compId, Long eventId, String role) throws AccessForbiddenException;

    void removeEvent(Long compId, Long eventId, String role) throws AccessForbiddenException;

    void pinCompilation(Long compId, String role) throws AccessForbiddenException;

    void unpinCompilation(Long compId, String role) throws AccessForbiddenException;
}
