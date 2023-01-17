package ru.practicum.explore_with_me.ewm_main_service.request.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.explore_with_me.ewm_main_service.request.dto.ParticipationRequestDto;
import ru.practicum.explore_with_me.ewm_main_service.request.model.Request;

import java.time.format.DateTimeFormatter;

@Component
public class RequestMapperImpl implements RequestMapper {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public ParticipationRequestDto toDto(Request request) {
        if (request == null) {
            return null;
        }

        ParticipationRequestDto dto = new ParticipationRequestDto();

        dto.setId(request.getId());
        dto.setRequester(request.getRequester().getId());
        dto.setCreated(request.getCreated().format(dateTimeFormatter));
        dto.setEvent(request.getEvent().getId());
        dto.setStatus(request.getStatus().name());

        return dto;
    }
}
