package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.dto.newDto.NewCategoryDto;

import java.util.Collection;

public interface CategoryService {

    //***ADMIN METHOD'S***
    CategoryDto create(NewCategoryDto dto);
    CategoryDto update(CategoryDto dto);
    void delete(long id);

    //***PUBLIC METHOD'S***
    Collection<CompilationDto> getAll(int from, int size);

    CompilationDto get(long catId);
}
