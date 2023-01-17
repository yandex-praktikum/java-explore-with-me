package ru.practicum.explore_with_me.ewm_main_service.request.service;

import ru.practicum.explore_with_me.ewm_main_service.exceptions.AccessForbiddenException;
import ru.practicum.explore_with_me.ewm_main_service.exceptions.ConflictArgumentsException;
import ru.practicum.explore_with_me.ewm_main_service.request.dto.ParticipationRequestDto;
import ru.practicum.explore_with_me.ewm_main_service.request.model.Request;

import java.util.List;

public interface RequestService {

    List<Request> findRequest(Long userId);

    List<ParticipationRequestDto> findRequest(Long userId, String role) throws AccessForbiddenException;

    List<Request> findRequests(Long eventId, Long userId);

    List<ParticipationRequestDto> findRequests(Long eventId, Long userId, String role) throws AccessForbiddenException;

    Request addRequest(Long eventId, Long userId) throws ConflictArgumentsException;

    ParticipationRequestDto addRequest(Long eventId, Long userId, String role) throws AccessForbiddenException;

    Request cancelRequest(Long requestId, Long userId);

    ParticipationRequestDto cancelRequest(Long requestId, Long userId, String role) throws AccessForbiddenException;

    Request rejectRequest(Long eventId, Long requestId, Long userId);

    ParticipationRequestDto rejectRequest(Long eventId, Long requestId, Long userId, String role) throws AccessForbiddenException;

    Request confirmRequest(Long eventId, Long requestId, Long userId);

    ParticipationRequestDto confirmRequest(Long eventId, Long requestId, Long userId, String role) throws AccessForbiddenException;

}
