package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.UserDto;
import ru.practicum.ewm.dto.newDto.NewUserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAll(List<Long> ids, int from, int size);

    UserDto create(NewUserDto dto);

    void delete(long userId);
}
