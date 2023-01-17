package ru.practicum.explore_with_me.ewm_main_service.user.mapper;

import org.mapstruct.Mapper;
import ru.practicum.explore_with_me.ewm_main_service.user.dto.NewUserDto;
import ru.practicum.explore_with_me.ewm_main_service.user.dto.UserDto;
import ru.practicum.explore_with_me.ewm_main_service.user.model.User;

@Mapper
public interface UserMapper {
    User toUser(NewUserDto dto);

    UserDto toDto(User user);
}
