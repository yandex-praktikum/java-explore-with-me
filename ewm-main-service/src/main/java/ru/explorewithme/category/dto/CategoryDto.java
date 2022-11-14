package ru.explorewithme.category.dto;

import lombok.Getter;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
public class CategoryDto {
    @Positive
    @NonNull
    private Long id;
    @NotBlank
    private String name;

    public CategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
