package ru.practicum.explore_with_me.ewm_main_service.category.service;

import ru.practicum.explore_with_me.ewm_main_service.category.dto.CategoryDto;
import ru.practicum.explore_with_me.ewm_main_service.category.dto.NewCategoryDto;
import ru.practicum.explore_with_me.ewm_main_service.category.model.Category;
import ru.practicum.explore_with_me.ewm_main_service.exceptions.AccessForbiddenException;

import java.util.List;

public interface CategoryService {

    Category createCategory(Category category);

    CategoryDto createCategory(NewCategoryDto categoryDto, String role) throws AccessForbiddenException;

    Category getCategory(Long catId);

    CategoryDto getCategoryToDto(Long catId);

    Category editCategory(Category category);

    CategoryDto editCategoryToDto(CategoryDto category, String role) throws AccessForbiddenException;

    void removeCategory(Long catId, String role) throws RuntimeException;

    List<Category> findCategories(Integer from, Integer size);

    List<CategoryDto> findCategoriesToDto(Integer from, Integer size);

}
