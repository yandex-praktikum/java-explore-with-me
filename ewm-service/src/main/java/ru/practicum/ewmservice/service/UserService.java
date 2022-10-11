package ru.practicum.ewmservice.service;

import ru.practicum.ewmservice.dto.UserDto;
import ru.practicum.ewmservice.dto.newDto.NewUserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAll(List<Long> ids, int from, int size);

    UserDto create(NewUserDto dto);

    void delete(long userId);
}
