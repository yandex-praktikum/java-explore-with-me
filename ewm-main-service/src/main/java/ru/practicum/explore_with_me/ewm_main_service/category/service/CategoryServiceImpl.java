package ru.practicum.explore_with_me.ewm_main_service.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.ewm_main_service.category.dto.CategoryDto;
import ru.practicum.explore_with_me.ewm_main_service.category.dto.NewCategoryDto;
import ru.practicum.explore_with_me.ewm_main_service.category.mapper.CategoryMapper;
import ru.practicum.explore_with_me.ewm_main_service.category.model.Category;
import ru.practicum.explore_with_me.ewm_main_service.category.repository.CategoryRepository;
import ru.practicum.explore_with_me.ewm_main_service.exceptions.AccessForbiddenException;
import ru.practicum.explore_with_me.ewm_main_service.exceptions.ConflictArgumentsException;
import ru.practicum.explore_with_me.ewm_main_service.exceptions.NotFoundException;
import ru.practicum.explore_with_me.ewm_main_service.handler.Error;
import ru.practicum.explore_with_me.ewm_main_service.utils.RoleEnum;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.explore_with_me.ewm_main_service.utils.ParametersValid.pageValidated;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public Category createCategory(Category category) {
        if (categoryRepository.existsCategoriesByName(category.getName())) {
            throw new ConflictArgumentsException(this.getClass().getName(),
                    "Category already exist", "Категория с заданным именем уже существует.");
        }
        Category createdCategory = categoryRepository.save(category);
        log.debug("{} has been added.", createdCategory);
        return createdCategory;
    }

    @Override
    @Transactional
    public CategoryDto createCategory(NewCategoryDto categoryDto, String role) throws AccessForbiddenException {
        if (RoleEnum.of(role) == RoleEnum.ADMINISTRATOR) {
            Category category = categoryMapper.toCategory(categoryDto);
            category = createCategory(category);
            return categoryMapper.toDto(category);
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }

    @Override
    public Category getCategory(Long catId) {
        return getCategoryOrThrow(catId);
    }

    @Override
    public CategoryDto getCategoryToDto(Long catId) {
        return categoryMapper.toDto(getCategory(catId));
    }

    @Override
    @Transactional
    public Category editCategory(Category category) {
        if (categoryRepository.existsCategoriesByName(category.getName())) {
            throw new ConflictArgumentsException(this.getClass().getName(),
                    "Category already exist", "Категория с заданным именем уже существует.");
        }
        log.debug("{} has been update.", category);
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public CategoryDto editCategoryToDto(CategoryDto categoryDto, String role) throws AccessForbiddenException {
        if (RoleEnum.of(role) == RoleEnum.ADMINISTRATOR) {
            Category category = categoryMapper.toCategory(categoryDto);
            return categoryMapper.toDto(editCategory(category));
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }

    @Override
    public void removeCategory(Long catId, String role) throws RuntimeException {
        if (RoleEnum.of(role) == RoleEnum.ADMINISTRATOR) {
            Category category = getCategoryOrThrow(catId);
            categoryRepository.delete(category);
            log.debug("{} has been deleted.", category);
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }

    @Override
    public List<Category> findCategories(Integer from, Integer size) {
        Optional<PageRequest> pageRequest = pageValidated(from, size);
        return pageRequest
                .map(request -> categoryRepository.findAll(request).toList())
                .orElseGet(categoryRepository::findAll);
    }

    @Override
    public List<CategoryDto> findCategoriesToDto(Integer from, Integer size) {
        return findCategories(from, size)
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }


    private Category getCategoryOrThrow(Long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(List.of(new Error(this.getClass().getName(),
                        String.format("Category %d not found.", catId))),
                        "Категория с заданным индексом отсутствует.",
                        String.format("Category %d not found.", catId)));
    }
}
