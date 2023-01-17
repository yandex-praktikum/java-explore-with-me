package ru.practicum.explore_with_me.ewm_main_service.request.mapper;

import ru.practicum.explore_with_me.ewm_main_service.request.dto.ParticipationRequestDto;
import ru.practicum.explore_with_me.ewm_main_service.request.model.Request;


public interface RequestMapper {

    ParticipationRequestDto toDto(Request request);

}
