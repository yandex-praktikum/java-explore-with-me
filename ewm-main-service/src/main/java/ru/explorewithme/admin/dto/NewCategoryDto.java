package ru.explorewithme.admin.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class NewCategoryDto {
    @NotBlank(message = "name can't be blank")
    private String name;

    @Override
    public String toString() {
        return "NewCategoryDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
