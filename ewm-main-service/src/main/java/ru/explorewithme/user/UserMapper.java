package ru.explorewithme.user;

import ru.explorewithme.user.dto.NewUserRequest;
import ru.explorewithme.user.dto.UserDto;
import ru.explorewithme.user.dto.UserShortDto;
import ru.explorewithme.user.model.User;

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
