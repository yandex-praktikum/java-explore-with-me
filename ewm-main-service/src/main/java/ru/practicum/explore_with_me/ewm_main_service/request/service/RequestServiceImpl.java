package ru.practicum.explore_with_me.ewm_main_service.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.ewm_main_service.event.model.Event;
import ru.practicum.explore_with_me.ewm_main_service.event.model.EventStateEnum;
import ru.practicum.explore_with_me.ewm_main_service.event.repository.EventRepository;
import ru.practicum.explore_with_me.ewm_main_service.exceptions.AccessForbiddenException;
import ru.practicum.explore_with_me.ewm_main_service.exceptions.ConflictArgumentsException;
import ru.practicum.explore_with_me.ewm_main_service.exceptions.NotFoundException;
import ru.practicum.explore_with_me.ewm_main_service.handler.Error;
import ru.practicum.explore_with_me.ewm_main_service.request.dto.ParticipationRequestDto;
import ru.practicum.explore_with_me.ewm_main_service.request.mapper.RequestMapper;
import ru.practicum.explore_with_me.ewm_main_service.request.model.Request;
import ru.practicum.explore_with_me.ewm_main_service.request.model.RequestStatusEnum;
import ru.practicum.explore_with_me.ewm_main_service.request.repository.RequestRepository;
import ru.practicum.explore_with_me.ewm_main_service.user.model.User;
import ru.practicum.explore_with_me.ewm_main_service.user.repository.UserRepository;
import ru.practicum.explore_with_me.ewm_main_service.utils.RoleEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public List<Request> findRequest(Long userId) {
        return requestRepository.getByRequesterId(userId);
    }

    @Override
    public List<ParticipationRequestDto> findRequest(Long userId, String role) throws AccessForbiddenException {
        if (RoleEnum.of(role) == RoleEnum.USER) {
            return findRequest(userId)
                    .stream()
                    .map(requestMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }

    @Override
    public List<Request> findRequests(Long eventId, Long userId) {
        return requestRepository.getRequestsByEventAndInitiator(eventId, userId);
    }

    @Override
    public List<ParticipationRequestDto> findRequests(Long eventId, Long userId, String role) throws AccessForbiddenException {
        if (RoleEnum.of(role) == RoleEnum.USER) {
            return findRequests(eventId, userId)
                    .stream()
                    .map(requestMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }

    @Transactional
    @Override
    public Request addRequest(Long eventId, Long userId) throws ConflictArgumentsException {
        Event event = eventRepository.getById(eventId);
        if (!event.isAvailable()) {
            log.error("Maximum value exceeded.");
            throw new ConflictArgumentsException(this.getClass().getName(),
                    "Unable to add an event request", "Невозможно добавить запрос на участие в событии.");
        }
        User user = getUserOrThrow(userId);
        if (requestRepository.getRequestByEventAndRequester(event, user).isPresent()) {
            log.error("Request for user id{} to participate in event id{} already exists.", userId, event.getId());
            throw new ConflictArgumentsException(this.getClass().getName(),
                    "Request already exist", "Запрос пользователя на участие в заданном событии уже существует.");
        } else if (event.getInitiator().getId().equals(userId)) {
            log.error("User id{} cannot submit a request to participate in his event id{}.", userId,
                    event.getId());
            throw new ConflictArgumentsException(this.getClass().getName(),
                    "User cannot submit a request to participate in their event",
                    "Пользователь не может создать запрос на участие в созданном им событии.");
        } else if (!event.getState().equals(EventStateEnum.PUBLISHED)) {
            log.error("You cannot participate in an unpublished event.");

            throw new ConflictArgumentsException(this.getClass().getName(),
                    "State of request must be PUBLISHED",
                    "You cannot participate in an unpublished event.");
        }
        Request request = new Request(null, LocalDateTime.now(), event, user, RequestStatusEnum.PENDING);
        if (!event.getRequestModeration()) {
            request.setStatus(RequestStatusEnum.CONFIRMED);
        }
        Request savedRequest = requestRepository.save(request);
        log.info("Добавлен запрос id{} на участие в событии id{}.", savedRequest.getId(), event.getId());
        return savedRequest;
    }

    @Override
    public ParticipationRequestDto addRequest(Long eventId, Long userId, String role) throws AccessForbiddenException {
        if (RoleEnum.of(role) == RoleEnum.USER) {
            return requestMapper.toDto(addRequest(eventId, userId));
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }

    @Transactional
    @Override
    public Request cancelRequest(Long requestId, Long userId) throws AccessForbiddenException {
        Request request = getRequestOrThrow(requestId);
        if (request.getRequester().getId().equals(userId)) {
            request.setStatus(RequestStatusEnum.CANCELED);
            return requestRepository.save(request);
        } else {
            throw new AccessForbiddenException(this, userId);
        }
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long requestId, Long userId, String role) throws AccessForbiddenException {
        if (RoleEnum.of(role) == RoleEnum.USER) {
            return requestMapper.toDto(cancelRequest(requestId, userId));
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }


    @Transactional
    @Override
    public Request rejectRequest(Long eventId, Long requestId, Long userId) throws AccessForbiddenException {
        Request request = getRequestOrThrow(requestId);
        if (request.getEvent().getInitiator().getId().equals(userId)) {
            request.setStatus(RequestStatusEnum.REJECTED);
            return requestRepository.save(request);
        } else {
            throw new AccessForbiddenException(this, userId);
        }
    }

    @Override
    public ParticipationRequestDto rejectRequest(Long eventId, Long requestId, Long userId, String role) throws AccessForbiddenException {
        if (RoleEnum.of(role) == RoleEnum.USER) {
            return requestMapper.toDto(rejectRequest(eventId, requestId, userId));
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }

    @Transactional
    @Override
    public Request confirmRequest(Long eventId, Long requestId, Long userId) throws AccessForbiddenException {
        Request request = getRequestOrThrow(requestId);
        if (request.getEvent().getInitiator().getId().equals(userId)) {
            request.setStatus(RequestStatusEnum.CONFIRMED);
            return requestRepository.save(request);
        } else {
            throw new AccessForbiddenException(this, userId);
        }
    }

    @Override
    public ParticipationRequestDto confirmRequest(Long eventId, Long requestId, Long userId, String role) throws AccessForbiddenException {
        if (RoleEnum.of(role) == RoleEnum.USER) {
            return requestMapper.toDto(confirmRequest(eventId, requestId, userId));
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }

    private Request getRequestOrThrow(Long requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException(List.of(new Error(this.getClass().getName(),
                        String.format("Request %d not found.", requestId))),
                        "Запрос с заданным индексом отсутствует.",
                        String.format("Request %d not found.", requestId)));
    }

    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(List.of(new Error(this.getClass().getName(),
                        String.format("User %d not found.", userId))),
                        "Пользователь с заданным индексом отсутствует.",
                        String.format("User %d not found.", userId)));
    }
}
