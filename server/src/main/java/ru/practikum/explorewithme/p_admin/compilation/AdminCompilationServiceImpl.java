package ru.practikum.explorewithme.p_admin.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practikum.explorewithme.dto.CompilationDto;
import ru.practikum.explorewithme.dto.in.NewCompilationDto;
import ru.practikum.explorewithme.exception.ObjNotFoundException;
import ru.practikum.explorewithme.model.Compilation;
import ru.practikum.explorewithme.model.Event;
import ru.practikum.explorewithme.repository.CompilationRepository;
import ru.practikum.explorewithme.repository.EventsRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practikum.explorewithme.mapper.CompilationMapper.toCompilation;
import static ru.practikum.explorewithme.mapper.CompilationMapper.toDto;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AdminCompilationServiceImpl implements AdminCompilationService{

    private final CompilationRepository repository;

    private final EventsRepository eventsRepository;

    @Override
    @Transactional
    public CompilationDto createCompilation(NewCompilationDto dto) {
        List<Event> events = eventsRepository.findAllById(dto.getEvents());
        return toDto(repository.save(toCompilation(dto, events)));
    }

    @Override
    @Transactional
    public void deleteCompilation(long compId) {
        repository.deleteById(compId);
    }

    @Override
    @Transactional
    public void deleteEventFromCompilation(long compId, long eventId) {
        Compilation compilation = repository.findById(compId)
                .orElseThrow(() -> new ObjNotFoundException("Подборка не найдена"));
        List<Event> events = compilation.getEvents().stream()
                .filter(event -> event.getId() != eventId)
                .collect(Collectors.toList());
        compilation.setEvents(events);
        repository.save(compilation);
    }

    @Override
    @Transactional
    public void addEventToCompilation(long compId, long eventId) {
        Compilation compilation = repository.findById(compId)
                .orElseThrow(() -> new ObjNotFoundException("Подборка не найдена"));
        Event event = eventsRepository.findById(eventId)
                .orElseThrow(() -> new ObjNotFoundException("Событие не найдено"));
        List<Event> events = compilation.getEvents();
        events.add(event);
        compilation.setEvents(events);
        repository.save(compilation);
    }

    @Override
    @Transactional
    public void unpinToMainPage(long compId) {
        Compilation compilation = repository.findById(compId)
                .orElseThrow(() -> new ObjNotFoundException("Подборка не найдена"));
        compilation.setPinned(false);
        repository.save(compilation);
    }

    @Override
    @Transactional
    public void pinToMainPage(long compId) {
        Compilation compilation = repository.findById(compId)
                .orElseThrow(() -> new ObjNotFoundException("Подборка не найдена"));
        compilation.setPinned(true);
        repository.save(compilation);
    }
}
