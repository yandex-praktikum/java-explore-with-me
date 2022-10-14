package ru.practikum.explorewithme.p_private.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practikum.explorewithme.dto.EventFullDto;
import ru.practikum.explorewithme.dto.EventShortDto;
import ru.practikum.explorewithme.dto.ParticipationRequestDto;
import ru.practikum.explorewithme.dto.in.NewEventDto;
import ru.practikum.explorewithme.dto.in.UpdateEventRequest;
import ru.practikum.explorewithme.exception.BadRequestException;
import ru.practikum.explorewithme.exception.ObjNotFoundException;
import ru.practikum.explorewithme.model.*;
import ru.practikum.explorewithme.pageable.OffsetLimitPageable;
import ru.practikum.explorewithme.repository.*;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practikum.explorewithme.mapper.EventMapper.*;
import static ru.practikum.explorewithme.mapper.ParticipationMapper.toDto;
import static ru.practikum.explorewithme.util.ValidDateTime.getDateTime;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PrivateEventServiceImpl implements PrivateEventService{

    private final EventsRepository repository;

    private final LocationRepository locationRepository;

    private final ParticipationRepository participationRepository;

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    @Override
    public List<EventShortDto> getEvents(long userId, Integer from, Integer size) {
        Pageable pageable = OffsetLimitPageable.of(from, size);
        return toShortDto(repository.findEventsByInitiator_Id(userId, pageable));
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(long userId, UpdateEventRequest event) {
        Event oldEvent = repository.findById(event.getEventId())
                .orElseThrow(() -> new ObjNotFoundException("Объект для обновления не найден"));
        if (oldEvent.getInitiator().getId() != userId) {
            throw new BadRequestException("Пользователю запрещено редактировать данное событие");
        }
        if (oldEvent.getStatus().equals(EventStatus.PUBLISHED))
            throw new BadRequestException("Событие уже опубликовано, обновление запрещено");
        if (event.getAnnotation() != null) {
            oldEvent.setAnnotation(event.getAnnotation());
        }
        if (event.getCategory() != null) {
            oldEvent.getCategory().setId(event.getCategory());
        }
        if (event.getDescription() != null) {
            oldEvent.setDescription(event.getDescription());
        }
        if (event.getEventDate() != null) {
            oldEvent.setEventDate(getDateTime(event.getEventDate()));
        }
        if (event.getPaid() != null) {
            oldEvent.setPaid(event.getPaid());
        }
        if (event.getParticipantLimit() != null) {
            oldEvent.setParticipantLimit(event.getParticipantLimit());
        }
        if (event.getTitle() != null) {
            oldEvent.setTitle(event.getTitle());
        }
        oldEvent.setStatus(EventStatus.PENDING);
        return toFullDto(repository.save(oldEvent));
    }

    @Override
    @Transactional
    public EventFullDto createEvent(long userId, NewEventDto newEventDto) {
        User initiator = userRepository.findById(userId)
                .orElseThrow(() -> new ObjNotFoundException("Пользователь ненайден"));
        Category category = categoryRepository.findById(newEventDto.getCategory())
                .orElseThrow(() -> new ObjNotFoundException("Категория не найдена"));
        Event event = toEvent(newEventDto, category);
        event.setCreatedOn(LocalDateTime.now());
        event.setInitiator(initiator);
        event.setStatus(EventStatus.PENDING);
        event.setLocation(locationRepository.save(event.getLocation()));
        return toFullDto(repository.save(event));
    }

    @Override
    public EventFullDto getEvent(long userId, long eventId) {
        return toFullDto(repository.findEventByIdAndInitiator_Id(eventId, userId)
                .orElseThrow(() -> new ObjNotFoundException("Объект не найден")));
    }

    @Override
    @Transactional
    public EventFullDto cancelEvent(long userId, long eventId) {
        Event event = repository.findEventByIdAndInitiator_Id(eventId, userId)
                .orElseThrow(() -> new ObjNotFoundException("Объект не найден"));
        event.setStatus(EventStatus.CANCELED);
        return toFullDto(repository.save(event));
    }

    @Override
    public List<ParticipationRequestDto> getEventRequests(long userId, long eventId) {
        return toDto(participationRepository.findByEvent_idAndEvent_Initiator_Id(eventId, userId));
    }

    @Override
    @Transactional
    public ParticipationRequestDto confirmEventRequest(long userId, long eventId, long reqId) {
        Participation participation = participationRepository
                .findByIdAndEvent_IdAndEvent_Initiator_Id(reqId, eventId, userId)
                .orElseThrow(() -> new ObjNotFoundException("Ресурс не найден"));
        if (participation.getStatus().equals(ParticipationStatus.CONFIRMED)) {
            throw new BadRequestException("Заявка уже подтверждена");
        }
        if (participation.getEvent().getParticipantLimit() > 0 &&
                participation.getEvent().getConfirmedRequests() == participation.getEvent().getParticipantLimit()) {
            rejectEventRequests(eventId);
            throw new BadRequestException("Достигнут лимит на подтверждение");
        }
        participation.getEvent().setConfirmedRequests(participation.getEvent().getConfirmedRequests() + 1);
        participation.setStatus(ParticipationStatus.CONFIRMED);
        repository.save(participation.getEvent());
        return toDto(participationRepository.save(participation));
    }

    @Override
    public ParticipationRequestDto rejectEventRequest(long userId, long eventId, long reqId) {
        Participation participation = participationRepository
                .findByIdAndEvent_IdAndEvent_Initiator_Id(reqId, eventId, userId)
                .orElseThrow(() -> new ObjNotFoundException("Ресурс не найден"));
        if (participation.getStatus().equals(ParticipationStatus.CONFIRMED)) {
            participation.getEvent().setConfirmedRequests(participation.getEvent().getConfirmedRequests() - 1);
        }
        participation.setStatus(ParticipationStatus.REJECTED);
        repository.save(participation.getEvent());
        return toDto(participationRepository.save(participation));
    }

    private void rejectEventRequests(long eventId) {
        List<Participation> list = participationRepository
                .findByStatusAndId(ParticipationStatus.PENDING, eventId);
        for (Participation el : list) {
            el.setStatus(ParticipationStatus.REJECTED);
        }
        participationRepository.saveAll(list);
    }
}
