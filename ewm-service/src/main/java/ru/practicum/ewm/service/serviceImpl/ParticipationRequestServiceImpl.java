package ru.practicum.ewm.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.ParticipationRequestDto;
import ru.practicum.ewm.exceptions.EwmObjNotFoundException;
import ru.practicum.ewm.model.ParticipationRequest;
import ru.practicum.ewm.model.enums.RequestStatus;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.PartRequestRepository;
import ru.practicum.ewm.repository.UserRepository;
import ru.practicum.ewm.service.ParticipationRequestService;
import ru.practicum.ewm.service.mapper.ParticipationRequestMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ParticipationRequestServiceImpl implements ParticipationRequestService {
    private PartRequestRepository repository;
    private UserRepository userRepository;
    private EventRepository eventRepository;

    @Override
    public List<ParticipationRequestDto> getParticipationRequestsByUserId(long requestorId) {
        return repository.findAllByRequestorId(requestorId)
                .stream()
                .map(ParticipationRequestMapper::toPartDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto postPartRequest(long userId, long eventId) {
        ParticipationRequest request = new ParticipationRequest();
        request.setCreated(LocalDateTime.now());

        request.setRequestor(userRepository
                .findById(userId)
                .orElseThrow(() -> new EwmObjNotFoundException(String.format("User with id=%d was not found", userId))));

        request.setEvent(eventRepository
                .findById(eventId)
                .orElseThrow(() -> new EwmObjNotFoundException(String.format("Event with id=%d was not found", eventId))));

        request.setStatus(RequestStatus.PENDING);
        return ParticipationRequestMapper.toPartDto(repository.save(request));
    }

    @Override
    public ParticipationRequestDto cancelMainPartRequest(long userId, long requestId) {
        ParticipationRequest part = repository
                .findByIdAndRequestorId(requestId, userId)
                .orElseThrow(() -> new EwmObjNotFoundException(
                        String.format("Event where requestor id=%d and request id=%d was not found",
                                requestId,
                                userId)));

        part.setStatus(RequestStatus.CANCELED);
        return ParticipationRequestMapper.toPartDto(repository.save(part));
    }
}
