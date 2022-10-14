package ru.practikum.explorewithme.dto.in;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class NewUserRequest {

    @NotNull
    @Size(min=1, max=100)
    @Email(message = "Email not valid")
    private String email;

    @NotNull
    @Size(min=1, max=100)
    private String name;
}
