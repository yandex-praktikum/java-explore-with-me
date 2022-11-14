package ru.explorewithme.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserShortDto {
    private Long id;
    private String name;

    @Override
    public String toString() {
        return "UserShortDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
