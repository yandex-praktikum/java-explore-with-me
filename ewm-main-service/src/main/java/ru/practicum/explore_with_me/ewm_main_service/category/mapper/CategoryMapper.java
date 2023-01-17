package ru.practicum.explore_with_me.ewm_main_service.category.mapper;

import org.mapstruct.Mapper;
import ru.practicum.explore_with_me.ewm_main_service.category.dto.CategoryDto;
import ru.practicum.explore_with_me.ewm_main_service.category.dto.NewCategoryDto;
import ru.practicum.explore_with_me.ewm_main_service.category.model.Category;

@Mapper
public interface CategoryMapper {

    Category toCategory(NewCategoryDto dto);

    Category toCategory(CategoryDto dto);

    CategoryDto toDto(Category category);
}
