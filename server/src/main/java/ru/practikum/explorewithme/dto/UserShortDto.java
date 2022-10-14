package ru.practikum.explorewithme.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserShortDto {
    private long id;
    private String name;
}
