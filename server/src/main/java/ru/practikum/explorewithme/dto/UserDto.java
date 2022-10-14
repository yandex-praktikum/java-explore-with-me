package ru.practikum.explorewithme.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {

    private long id;

    private String email;

    private String name;
}
