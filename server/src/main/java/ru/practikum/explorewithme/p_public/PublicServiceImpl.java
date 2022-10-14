package ru.practikum.explorewithme.p_public;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practikum.explorewithme.EventsSort;
import ru.practikum.explorewithme.dto.CategoryDto;
import ru.practikum.explorewithme.dto.CompilationDto;
import ru.practikum.explorewithme.dto.EventFullDto;
import ru.practikum.explorewithme.dto.EventShortDto;
import ru.practikum.explorewithme.exception.ObjNotFoundException;
import ru.practikum.explorewithme.mapper.CompilationMapper;
import ru.practikum.explorewithme.mapper.EventMapper;
import ru.practikum.explorewithme.model.Compilation;
import ru.practikum.explorewithme.model.Event;
import ru.practikum.explorewithme.model.EventStatus;
import ru.practikum.explorewithme.repository.CategoryRepository;
import ru.practikum.explorewithme.repository.CompilationRepository;
import ru.practikum.explorewithme.repository.EventsRepository;
import ru.practikum.explorewithme.pageable.OffsetLimitPageable;
import ru.practikum.explorewithme.stat.StatClient;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practikum.explorewithme.mapper.CategoryMapper.toDto;
import static ru.practikum.explorewithme.mapper.CompilationMapper.toDto;
import static ru.practikum.explorewithme.mapper.EventMapper.toFullDto;
import static ru.practikum.explorewithme.p_public.SpecificationForFind.*;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PublicServiceImpl implements PublicService {

    private final CategoryRepository categoryRepository;

    private final EventsRepository eventsRepository;

    private final CompilationRepository compilationRepository;

    private final StatClient statClient;

    @Override
    public List<EventShortDto> getEvents(String textIn,
                                         Long[] categories,
                                         Optional<Boolean> paidIn,
                                         String rangeStart,
                                         String rangeEnd,
                                         Boolean onlyAvailable,
                                         String eventsSort,
                                         Integer from,
                                         Integer size,
                                         HttpServletRequest request) {
        String sort = "";
        if (eventsSort.equals(EventsSort.EVENT_DATE.name())) sort = "eventDate";
        if (eventsSort.equals(EventsSort.VIEWS.name())) sort = "views";
        Pageable pageable = OffsetLimitPageable.of(from, size, Sort.by(Sort.Direction.ASC, sort));
        statClient.saveEndpointHit(request);
        return eventsRepository.findAll(
                statusFilter(EventStatus.PUBLISHED)
                .and(categoriesFilter(categories))
                .and(rangeTimeFilter(rangeStart, rangeEnd))
                .and(textFilter(textIn))
                .and(paidFilter(paidIn))
                .and(availableFilter(onlyAvailable)),
                pageable
        ).toList().stream().map(EventMapper::toShortDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEvent(long eventId, HttpServletRequest request) {
        Event event = eventsRepository.findById(eventId)
                .orElseThrow(() -> new ObjNotFoundException("Ресурс не найден"));
        statClient.saveEndpointHit(request);
        return toFullDto(event);
    }

    @Override
    public List<CompilationDto> getCompilations(Optional<Boolean> pinned, Integer from, Integer size) {
        Pageable pageable = OffsetLimitPageable.of(from, size);
        List<Compilation> allComp = compilationRepository.findAll(pinnedFilter(pinned), pageable).toList();
        addInfoInCompilationAboutEvents(allComp);
        return allComp.stream().map(CompilationMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilation(long compId) {
        return toDto(compilationRepository.findById(compId)
                .orElseThrow(() -> new ObjNotFoundException("Ресурс не найден")));
    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        Pageable pageable = OffsetLimitPageable.of(from, size);
        return toDto(categoryRepository.findAll(pageable).toList());
    }

    @Override
    public CategoryDto getCategory(long catId) {
        return toDto(categoryRepository.findById(catId).
                orElseThrow(() -> new ObjNotFoundException("Ресурс не найден")));
    }

    private void addInfoInCompilationAboutEvents(List<Compilation> allComp) {
        for (Compilation comp : allComp) {
            comp.setEvents(eventsRepository.findEventsByCompilationsId(comp.getId()));
        }
    }
}
