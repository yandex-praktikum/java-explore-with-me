package ru.practicum.ewm.dto.newDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @Email
    private String email;
}
