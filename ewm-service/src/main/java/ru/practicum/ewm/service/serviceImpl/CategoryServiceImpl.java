package ru.practicum.ewm.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.newDto.NewCategoryDto;
import ru.practicum.ewm.exceptions.EwmObjNotFoundException;
import ru.practicum.ewm.exceptions.NotUniqueException;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.repository.CategoryRepository;
import ru.practicum.ewm.service.CategoryService;
import ru.practicum.ewm.service.mapper.CategoryMapper;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository repository;

    @Override
    public CategoryDto create(NewCategoryDto dto) {
        return CategoryMapper.toCategoryDto(repository.save(CategoryMapper.fromNewCategoryDto(dto)));
    }

    @Override
    public CategoryDto update(CategoryDto dto) {
        Category cat = repository
                .findById(dto.getId())
                .orElseThrow(() -> new EwmObjNotFoundException(String.format("Category with id=%d was not found", dto.getId())));

        if (repository.findByName(dto.getName()).isPresent()) {
            throw new NotUniqueException(String.format("Name - %s is not unique", dto.getName()));
        }

        cat.setName(dto.getName());

        return CategoryMapper.toCategoryDto(repository.save(cat));
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public Collection<CategoryDto> getAll(int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return repository.findAll(pageable).stream().map(CategoryMapper::toCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto get(long catId) {
        Category cat = repository
                .findById(catId)
                .orElseThrow(() -> new EwmObjNotFoundException(String.format("Category with id=%d was not found", catId)));

        return CategoryMapper.toCategoryDto(cat);
    }
}
