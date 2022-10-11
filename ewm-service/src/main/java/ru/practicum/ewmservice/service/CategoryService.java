package ru.practicum.ewmservice.service;

import ru.practicum.ewmservice.dto.CategoryDto;
import ru.practicum.ewmservice.dto.newDto.NewCategoryDto;

import java.util.Collection;

public interface CategoryService {

    //***ADMIN METHOD'S*** ↓
    CategoryDto create(NewCategoryDto dto);

    CategoryDto update(CategoryDto dto);

    void delete(long id);

    //***PUBLIC METHOD'S*** ↓
    Collection<CategoryDto> getAll(int from, int size);

    CategoryDto get(long catId);
}
