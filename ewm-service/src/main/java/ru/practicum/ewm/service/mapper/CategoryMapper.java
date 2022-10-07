package ru.practicum.ewm.service.mapper;

import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.newDto.NewCategoryDto;
import ru.practicum.ewm.model.Category;

import java.time.LocalDateTime;

public class CategoryMapper {
    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public static Category fromNewCategoryDto(NewCategoryDto dto) {
        Category cat = new Category();
        cat.setName(dto.getName());
        cat.setCreated(LocalDateTime.now());
        return cat;
    }
}
