package ru.practikum.explorewithme.p_admin.user;

import ru.practikum.explorewithme.dto.UserDto;
import ru.practikum.explorewithme.model.User;

import java.util.List;

public interface AdminUserService {

    List<UserDto> getUsers(Long[] ids, Integer from, Integer size);

    UserDto createUser(User user);

    void deleteUser(long userId);
}
