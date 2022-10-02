package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.ParticipationRequestDto;

import java.util.List;

public interface ParticipationRequestService {
    List<ParticipationRequestDto> getParticipationRequestsByUserId(long userId);

    ParticipationRequestDto postPartRequest(long userId, long eventId);

    ParticipationRequestDto cancelMainPartRequest(long userId, long requestId);
}
