package ru.explorewithme.admin.dto;

import ru.explorewithme.admin.model.User;

public class UserDto extends User {
    public UserDto(Long id, String name, String email) {
        super(id, name, email);
    }
}
