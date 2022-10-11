package ru.practicum.ewmservice.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.dto.CompilationDto;
import ru.practicum.ewmservice.dto.newDto.NewCompilationDto;
import ru.practicum.ewmservice.exceptions.EwmObjNotFoundException;
import ru.practicum.ewmservice.model.Compilation;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.repository.CompilationRepository;
import ru.practicum.ewmservice.repository.EventRepository;
import ru.practicum.ewmservice.service.CompilationService;
import ru.practicum.ewmservice.service.mapper.CompilationMapper;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private CompilationRepository repository;
    private EventRepository eventRepository;
    private CompilationMapper mapper;

    @Override
    public CompilationDto create(NewCompilationDto newDto) {
        Compilation comp = mapper.toCompilationFromNewDto(newDto);

        if (newDto.getEvents() != null && newDto.getEvents().size() > 0) {
            comp.setEvents(eventRepository.findAllById(newDto.getEvents()));
        }

        return mapper.toCompilationDto(repository.save(comp));
    }

    @Override
    public void delete(long compId) {
        repository.deleteById(compId);
    }

    @Override
    @Transactional
    public void deleteEventFromComp(long compId, long eventId) {
        repository.deleteEventFromComp(compId, eventId);
    }

    @Override
    public CompilationDto addEventInComp(long compId, long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EwmObjNotFoundException(String.format("Event with id=%d was not found", eventId)));

        Compilation compilation = repository.findById(compId)
                .orElseThrow(() -> new EwmObjNotFoundException(String.format("Compilation with id=%d was not found", compId)));

        compilation.getEvents().add(event);
        return mapper.toCompilationDto(repository.save(compilation));
    }

    @Override
    @Transactional
    public CompilationDto installPin(long compId, boolean value) {
        get(compId);
        repository.installPin(value, compId);
        return mapper.toCompilationDto(repository.findById(compId).get());
    }

    @Override
    public Collection<CompilationDto> getAll(Boolean pinned, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return repository.findAllByPinned(pinned, pageable).stream().map(mapper::toCompilationDto).collect(Collectors.toList());
    }

    @Override
    public CompilationDto get(long compId) {
        Compilation comp = repository
                .findById(compId)
                .orElseThrow(() -> new EwmObjNotFoundException(String.format("Compilation with id=%d was not found", compId)));

        return mapper.toCompilationDto(comp);
    }
}
