package ru.practicum.ewmservice.service.mapper;

import ru.practicum.ewmservice.dto.UserDto;
import ru.practicum.ewmservice.dto.UserShortDto;
import ru.practicum.ewmservice.dto.newDto.NewUserDto;
import ru.practicum.ewmservice.model.User;

import java.time.LocalDateTime;

public class UserMapper {
    public static UserShortDto toUserShortDto(User initiator) {
        UserShortDto dto = new UserShortDto();
        dto.setId(initiator.getId());
        dto.setName(initiator.getName());
        return dto;
    }

    public static UserDto toUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public static User toUserFromNewDto(NewUserDto dto) {
        User user = new User();
        user.setCreated(LocalDateTime.now());
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        return user;
    }
}
