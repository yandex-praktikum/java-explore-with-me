package ru.practicum.ewm.service.mapper;

import ru.practicum.ewm.dto.ParticipationRequestDto;
import ru.practicum.ewm.model.ParticipationRequest;

public class ParticipationRequestMapper {

    public static ParticipationRequestDto toPartDto(ParticipationRequest part) {
        ParticipationRequestDto dto = new ParticipationRequestDto();
        dto.setId(part.getId());
        dto.setEvent(part.getEvent().getId());
        dto.setRequester(part.getRequestor().getId());
        dto.setStatus(part.getStatus());
        dto.setCreated(part.getCreated());
        return dto;
    }
}
