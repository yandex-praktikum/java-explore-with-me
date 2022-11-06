package ru.explorewithme.users.requests;

import ru.explorewithme.admin.model.User;
import ru.explorewithme.users.dto.ParticipationRequestDto;
import ru.explorewithme.users.model.Event;
import ru.explorewithme.users.model.Request;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RequestMapper {
    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        return ParticipationRequestDto.builder()
                .created("dddd")
                .event(request.getEvent().getId())
                .id(request.getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .build();
    }

    public static List<ParticipationRequestDto> toParticipationRequestDto(List<Request> requests) {
        List<ParticipationRequestDto> participationRequestDtos =
                requests.stream().map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());

        return participationRequestDtos;
    }
}
