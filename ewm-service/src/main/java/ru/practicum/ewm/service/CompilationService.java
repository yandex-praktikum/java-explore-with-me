package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.CompilationDto;

import java.util.Collection;

public interface CompilationService {
    //***ADMIN METHOD'S***
    CompilationDto create(CompilationDto dto);

    void delete(long compId);

    void deleteEventFromComp(long compId, long eventId);

    CompilationDto addEventInComp(long compId, long eventId);

    CompilationDto installPin(long compId, boolean value);

    //***PUBLIC METHOD'S***
    Collection<CompilationDto> getAll(Boolean pinned, int from, int size);

    CompilationDto get(long compId);
}
