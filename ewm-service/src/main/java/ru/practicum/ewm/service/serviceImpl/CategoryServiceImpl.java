package ru.practicum.ewm.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.dto.newDto.NewCategoryDto;
import ru.practicum.ewm.service.CategoryService;

import java.util.Collection;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    @Override
    public CategoryDto create(NewCategoryDto dto) {
        return null;
    }

    @Override
    public CategoryDto update(CategoryDto dto) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Collection<CompilationDto> getAll(int from, int size) {
        return null;
    }

    @Override
    public CompilationDto get(long catId) {
        return null;
    }
}
