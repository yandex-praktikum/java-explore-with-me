package ru.explorewithme.admin.categories;

import ru.explorewithme.admin.dto.CategoryDto;
import ru.explorewithme.admin.dto.NewCategoryDto;
import ru.explorewithme.admin.model.Category;

public class CategoryMapper {
    public static Category toCategory(NewCategoryDto newCategoryDto) {
        return new Category(null, newCategoryDto.getName());
    }

    public static Category toCategory(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName());
    }
    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}
