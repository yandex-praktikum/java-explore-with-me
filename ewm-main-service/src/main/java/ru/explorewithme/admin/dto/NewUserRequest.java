package ru.explorewithme.admin.dto;

import lombok.Getter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class NewUserRequest {
    @NotBlank(message = "name can't be blank")
    private String name;
    @NotBlank(message = "email can't be blank")
    @Pattern(regexp = ".+[@].+[.].+", message = "bad format of email")
    private String email;

    @Override
    public String toString() {
        return "NewUserRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
