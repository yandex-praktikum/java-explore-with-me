package ru.practicum.explore_with_me.ewm_main_service.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.ewm_main_service.compilation.dto.CompilationDto;
import ru.practicum.explore_with_me.ewm_main_service.compilation.dto.NewCompilationDto;
import ru.practicum.explore_with_me.ewm_main_service.compilation.mapper.CompilationMapper;
import ru.practicum.explore_with_me.ewm_main_service.compilation.model.Compilation;
import ru.practicum.explore_with_me.ewm_main_service.compilation.repository.CompilationRepository;
import ru.practicum.explore_with_me.ewm_main_service.event.model.Event;
import ru.practicum.explore_with_me.ewm_main_service.event.repository.EventRepository;
import ru.practicum.explore_with_me.ewm_main_service.exceptions.AccessForbiddenException;
import ru.practicum.explore_with_me.ewm_main_service.exceptions.ConflictArgumentsException;
import ru.practicum.explore_with_me.ewm_main_service.exceptions.NotFoundException;
import ru.practicum.explore_with_me.ewm_main_service.handler.Error;
import ru.practicum.explore_with_me.ewm_main_service.utils.RoleEnum;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.practicum.explore_with_me.ewm_main_service.utils.ParametersValid.pageValidated;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;

    private final CompilationMapper compilationMapper;

    private final EventRepository eventRepository;

    @Override
    public List<Compilation> findCompilations(Boolean pinned, Integer from, Integer size) {
        Optional<PageRequest> pageRequest = pageValidated(from, size);
        return pageRequest
                .map(request -> compilationRepository.findAllByPinned(pinned, request).toList())
                .orElseGet(() -> compilationRepository.findAllByPinned(pinned));
    }

    @Override
    public List<CompilationDto> findCompilationsToDto(Boolean pinned, Integer from, Integer size) {
        return findCompilations(pinned, from, size)
                .stream()
                .map(compilationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Compilation getCompilation(Long compId) {
        return getCompilationOrThrow(compId);
    }

    @Override
    public CompilationDto getCompilationToDto(Long compId) {
        return compilationMapper.toDto(getCompilation(compId));
    }

    @Transactional
    @Override
    public Compilation createCompilation(Compilation compilation) {
        Compilation createdCompilation = compilationRepository.save(compilation);
        log.debug("{} has been added.", createdCompilation);
        return createdCompilation;
    }

    @Override
    public CompilationDto createCompilation(NewCompilationDto compilationDto, String role) throws AccessForbiddenException {
        if (RoleEnum.of(role) == RoleEnum.ADMINISTRATOR) {
            Compilation compilation = compilationMapper.toCompilation(compilationDto);
            Set<Event> events = new HashSet<>();
            for (Long eventId : compilationDto.getEvents()) {
                events.add(eventRepository.getById(eventId));
            }
            compilation.setEvents(events);
            compilation = createCompilation(compilation);
            return compilationMapper.toDto(compilation);
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }

    @Transactional
    @Override
    public void removeCompilation(Long compId, String role) throws AccessForbiddenException {
        if (RoleEnum.of(role) == RoleEnum.ADMINISTRATOR) {
            Compilation compilation = getCompilationOrThrow(compId);
            compilationRepository.delete(compilation);
            log.debug("{} has been deleted.", compilation);
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }

    @Transactional
    @Override
    public Compilation addEvent(Long compId, Long eventId) {
        Compilation compilation = getCompilationOrThrow(compId);
        Event event = eventRepository.getById(eventId);
        if (!compilation.getEvents().contains(event)) {
            compilation.getEvents().add(event);
            log.debug("{} has been adding to event{}.", compilation, event);
            return compilationRepository.save(compilation);
        } else {
            log.error("В подборке id{} уже есть событие id{}.", compId, eventId);
            throw new ConflictArgumentsException(this.getClass().getName(), "The compilation already has an event.",
                    String.format("Событие с id %d уже есть в подборке id %d.", eventId, compId));
        }
    }

    @Override
    public CompilationDto addEvent(Long compId, Long eventId, String role) throws AccessForbiddenException {
        if (RoleEnum.of(role) == RoleEnum.ADMINISTRATOR) {
            return compilationMapper.toDto(addEvent(compId, eventId));
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }

    @Transactional
    @Override
    public void removeEvent(Long compId, Long eventId, String role) throws AccessForbiddenException {
        if (RoleEnum.of(role) == RoleEnum.ADMINISTRATOR) {
            Compilation compilation = getCompilationOrThrow(compId);
            Event event = eventRepository.getById(eventId);
            if (compilation.getEvents().contains(event)) {
                compilation.getEvents().remove(event);
                compilationRepository.save(compilation);
                log.info("Удалено событие id{} из подборки id{}.", eventId, compId);
            } else {
                throw new ConflictArgumentsException(this.getClass().getName(), "Event is not in compilation.",
                        String.format("Событие с id %d отсутствует в подборке id %d.", eventId, compId));
            }
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }

    @Transactional
    @Override
    public void pinCompilation(Long compId, String role) throws AccessForbiddenException {
        if (RoleEnum.of(role) == RoleEnum.ADMINISTRATOR) {
            Compilation compilation = getCompilationOrThrow(compId);
            compilation.setPinned(true);
            log.debug("{} has been pinned.", compilation);
            compilationRepository.save(compilation);
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }

    @Transactional
    @Override
    public void unpinCompilation(Long compId, String role) throws AccessForbiddenException {
        if (RoleEnum.of(role) == RoleEnum.ADMINISTRATOR) {
            Compilation compilation = getCompilationOrThrow(compId);
            compilation.setPinned(false);
            log.debug("{} has been unpinned.", compilation);
            compilationRepository.save(compilation);
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }

    private Compilation getCompilationOrThrow(Long compId) {
        return compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(List.of(new Error(this.getClass().getName(),
                        String.format("Compilation %d not found.", compId))),
                        "Подборка с заданным индексом отсутствует.",
                        String.format("Compilation %d not found.", compId)));
    }
}
