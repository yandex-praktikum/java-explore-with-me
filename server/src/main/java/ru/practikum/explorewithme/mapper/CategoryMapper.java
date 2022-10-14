package ru.practikum.explorewithme.mapper;

import ru.practikum.explorewithme.dto.CategoryDto;
import ru.practikum.explorewithme.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {

    public static CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static List<CategoryDto> toDto(List<Category> category) {
        List<CategoryDto> categoryDto = new ArrayList<>();
        for (Category el : category) {
            categoryDto.add(toDto(el));
        }
        return categoryDto;
    }
}
