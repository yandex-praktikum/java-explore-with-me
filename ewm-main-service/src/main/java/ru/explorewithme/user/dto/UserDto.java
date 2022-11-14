package ru.explorewithme.user.dto;

import ru.explorewithme.user.model.User;

public class UserDto extends User {
    public UserDto(Long id, String name, String email) {
        super(id, name, email);
    }
}
