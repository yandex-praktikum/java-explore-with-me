package ru.practicum.ewmservice.service;

import ru.practicum.ewmservice.dto.ParticipationRequestDto;

import java.util.List;

public interface ParticipationRequestService {
    List<ParticipationRequestDto> getParticipationRequestsByUserId(long userId);

    ParticipationRequestDto postPartRequest(long userId, long eventId);

    ParticipationRequestDto cancelMainPartRequest(long userId, long requestId);
}
