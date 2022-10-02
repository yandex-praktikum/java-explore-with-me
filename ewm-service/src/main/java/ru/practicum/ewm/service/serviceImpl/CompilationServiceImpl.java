package ru.practicum.ewm.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.service.CompilationService;

import java.util.Collection;

@Service
@AllArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    @Override
    public CompilationDto create(CompilationDto dto) {
        return null;
    }

    @Override
    public void delete(long compId) {

    }

    @Override
    public void deleteEventFromComp(long compId, long eventId) {

    }

    @Override
    public CompilationDto addEventInComp(long compId, long eventId) {
        return null;
    }

    @Override
    public CompilationDto installPin(long compId, boolean value) {
        return null;
    }

    @Override
    public Collection<CompilationDto> getAll(Boolean pinned, int from, int size) {
        return null;
    }

    @Override
    public CompilationDto get(long compId) {
        return null;
    }
}
