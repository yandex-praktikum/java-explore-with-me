package ru.practikum.explorewithme.p_private.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practikum.explorewithme.dto.ParticipationRequestDto;
import ru.practikum.explorewithme.exception.BadRequestException;
import ru.practikum.explorewithme.exception.ObjNotFoundException;
import ru.practikum.explorewithme.model.*;
import ru.practikum.explorewithme.repository.EventsRepository;
import ru.practikum.explorewithme.repository.ParticipationRepository;
import ru.practikum.explorewithme.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ru.practikum.explorewithme.mapper.ParticipationMapper.toDto;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PrivateRequestServiceImpl implements PrivateRequestService{

    private final ParticipationRepository repository;

    private final EventsRepository eventsRepository;

    private final UserRepository userRepository;

    @Override
    public List<ParticipationRequestDto> getRequests(long userId) {
        return toDto(repository.findByRequester_Id(userId));
    }

    @Override
    @Transactional
    public ParticipationRequestDto createRequest(long userId, long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjNotFoundException("Пользователь не найден"));
        Event event = eventsRepository.findById(eventId)
                .orElseThrow(() -> new ObjNotFoundException("Событие не найдено"));
        if (!event.getStatus().equals(EventStatus.PUBLISHED))
            throw new BadRequestException("Нельзя участвовать в неопубликованном событии");
        if (event.getConfirmedRequests() != 0 && event.getConfirmedRequests() == event.getParticipantLimit())
            throw new BadRequestException("Достигнут лимит запросов на участие");
        if (event.getInitiator().getId() == user.getId())
            throw new BadRequestException("Инициатор события не может добавить запрос на участие в своём событии");
        Optional<Participation> participation = repository.findByRequester_IdAndEvent_Id(userId, eventId);
        if (participation.isEmpty()) {
            ParticipationStatus status = ParticipationStatus.PENDING;
            if (!event.isRequestModeration()) {
                status = ParticipationStatus.CONFIRMED;
            }
            return toDto(repository.save(Participation.builder()
                    .created(LocalDateTime.now())
                    .event(event)
                    .requester(user)
                    .status(status)
                    .build()));
        } else {
            throw new BadRequestException("Нельзя добавить повторный запрос");
        }
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(long userId, long requestId) {
        Participation participation = repository.findByIdAndRequester_id(requestId, userId)
                .orElseThrow(() -> new ObjNotFoundException("Ресурс не найден"));
        participation.setStatus(ParticipationStatus.CANCELED);
        return toDto(repository.save(participation));
    }
}
