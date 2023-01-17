package ru.practicum.explore_with_me.ewm_main_service.user.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewUserDto {
    @NotBlank
    String name;
    @NotBlank
    String email;
}
