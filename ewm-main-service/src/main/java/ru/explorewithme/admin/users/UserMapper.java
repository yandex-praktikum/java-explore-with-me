package ru.explorewithme.admin.users;

import ru.explorewithme.admin.dto.NewUserRequest;
import ru.explorewithme.admin.dto.UserDto;
import ru.explorewithme.admin.dto.UserShortDto;
import ru.explorewithme.admin.model.User;

public class UserMapper {
    public static User toUser(NewUserRequest newUserRequest) {
        return new User(null, newUserRequest.getName(), newUserRequest.getEmail());
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public static UserShortDto toUserShortDto(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }
}
