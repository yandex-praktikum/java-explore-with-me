package ru.practikum.explorewithme.mapper;

import ru.practikum.explorewithme.dto.UserDto;
import ru.practikum.explorewithme.dto.UserShortDto;
import ru.practikum.explorewithme.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public static List<UserDto> toDto(List<User> users) {
        List<UserDto> usersDto = new ArrayList<>();
        for (User user : users) {
            usersDto.add(toDto(user));
        }
        return usersDto;
    }

    public static UserShortDto toShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
