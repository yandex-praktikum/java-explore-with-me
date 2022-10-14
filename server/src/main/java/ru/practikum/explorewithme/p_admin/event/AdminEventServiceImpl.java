package ru.practikum.explorewithme.p_admin.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practikum.explorewithme.EventsSort;
import ru.practikum.explorewithme.dto.EventFullDto;
import ru.practikum.explorewithme.dto.in.AdminUpdateEventRequest;
import ru.practikum.explorewithme.exception.BadRequestException;
import ru.practikum.explorewithme.exception.ObjNotFoundException;
import ru.practikum.explorewithme.mapper.EventMapper;
import ru.practikum.explorewithme.model.Category;
import ru.practikum.explorewithme.model.Event;
import ru.practikum.explorewithme.model.EventStatus;
import ru.practikum.explorewithme.pageable.OffsetLimitPageable;
import ru.practikum.explorewithme.repository.CategoryRepository;
import ru.practikum.explorewithme.repository.EventsRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practikum.explorewithme.mapper.EventMapper.toFullDto;
import static ru.practikum.explorewithme.model.EventStatus.eventsStatus;
import static ru.practikum.explorewithme.p_public.SpecificationForFind.*;
import static ru.practikum.explorewithme.util.ValidDateTime.getDateTime;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AdminEventServiceImpl implements AdminEventService {

    private final EventsRepository repository;

    private final CategoryRepository categoryRepository;

    @Override
    public List<EventFullDto> getEvents(Long[] users,
                                        String[] states,
                                        Long[] categories,
                                        String rangeStart,
                                        String rangeEnd,
                                        boolean onlyAvailable,
                                        EventsSort eventSort,
                                        Integer from,
                                        Integer size) {
        String sort = "";
        if (eventSort.equals(EventsSort.EVENT_DATE)) sort = "eventDate";
        if (eventSort.equals(EventsSort.VIEWS)) sort = "views";
        Pageable pageable = OffsetLimitPageable.of(from, size, Sort.by(Sort.Direction.ASC, sort));
        return repository.findAll(
                usersFilter(users)
                .and(statusFilter(eventsStatus(states)))
                .and(categoriesFilter(categories))
                .and(rangeTimeFilter(rangeStart, rangeEnd))
                , pageable
        ).toList().stream().map(EventMapper::toFullDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(long eventId, AdminUpdateEventRequest dto) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new ObjNotFoundException("Ресурс не найден"));
        if (dto.getAnnotation() != null)
            event.setAnnotation(dto.getAnnotation());
        if (dto.getCategory() != null && dto.getCategory() != 0) {
            Category category = categoryRepository.findById(dto.getCategory())
                    .orElseThrow(() -> new ObjNotFoundException("Категория не найдена"));
            event.setCategory(category);
        }
        if (dto.getDescription() != null)
            event.setDescription(dto.getDescription());
        if (dto.getEventDate() != null)
            event.setEventDate(getDateTime(dto.getEventDate()));
        if (dto.getLocation() != null) {
            event.getLocation().setLon(dto.getLocation().getLon());
            event.getLocation().setLat(dto.getLocation().getLat());
        }
        if (dto.getPaid() != null)
            event.setPaid(dto.getPaid());
        if (dto.getParticipantLimit() != null)
            event.setParticipantLimit(dto.getParticipantLimit());
        if (dto.getRequestModeration() != null)
            event.setRequestModeration(dto.getRequestModeration());

        return toFullDto(repository.save(event));
    }

    @Override
    @Transactional
    public EventFullDto publishEvent(long eventId) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new ObjNotFoundException("Ресурс не найден"));
        if (!event.getStatus().equals(EventStatus.PENDING)) throw new BadRequestException("Статус не PENDING");
        event.setStatus(EventStatus.PUBLISHED);
        return toFullDto(repository.save(event));
    }

    @Override
    public EventFullDto rejectEvent(long eventId) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new ObjNotFoundException("Ресурс не найден"));
        if (event.getStatus().equals(EventStatus.PUBLISHED)) throw new BadRequestException("Статус PUBLISHED");
        event.setStatus(EventStatus.CANCELED);
        return toFullDto(repository.save(event));
    }
}
