package ru.practicum.explore_with_me.ewm_main_service.user.service;

import ru.practicum.explore_with_me.ewm_main_service.exceptions.AccessForbiddenException;
import ru.practicum.explore_with_me.ewm_main_service.user.dto.NewUserDto;
import ru.practicum.explore_with_me.ewm_main_service.user.dto.UserDto;
import ru.practicum.explore_with_me.ewm_main_service.user.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    UserDto createUser(NewUserDto userDto, String role) throws AccessForbiddenException;

    User getUser(Long userId);

    UserDto getUser(Long userId, String role);

    List<User> findUser(Long[] ids, Integer from, Integer size);

    List<UserDto> findUser(Long[] ids, Integer from, Integer size, String role) throws AccessForbiddenException;

    void removeUser(Long userId, String role) throws AccessForbiddenException;

}
