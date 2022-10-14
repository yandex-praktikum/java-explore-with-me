package ru.practikum.explorewithme.mapper;

import ru.practikum.explorewithme.dto.ParticipationRequestDto;
import ru.practikum.explorewithme.model.Participation;

import java.util.ArrayList;
import java.util.List;

public class ParticipationMapper {

    public static ParticipationRequestDto toDto(Participation participation) {
        return ParticipationRequestDto.builder()
                .id(participation.getId())
                .created(participation.getCreated())
                .event(participation.getEvent().getId())
                .requester(participation.getRequester().getId())
                .status(participation.getStatus())
                .build();
    }

    public static List<ParticipationRequestDto> toDto(List<Participation> part) {
        List<ParticipationRequestDto> partDto = new ArrayList<>();
        for (Participation el : part) {
            partDto.add(toDto(el));
        }
        return partDto;
    }
}
