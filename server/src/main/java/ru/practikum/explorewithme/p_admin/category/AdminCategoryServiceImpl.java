package ru.practikum.explorewithme.p_admin.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practikum.explorewithme.dto.CategoryDto;
import ru.practikum.explorewithme.exception.ObjNotFoundException;
import ru.practikum.explorewithme.model.Category;
import ru.practikum.explorewithme.repository.CategoryRepository;

import static ru.practikum.explorewithme.mapper.CategoryMapper.toDto;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository repository;

    @Override
    @Transactional
    public CategoryDto updateCategory(Category category) {
        repository.findById(category.getId())
                .orElseThrow(() -> new ObjNotFoundException("Объект для обновления не найден"));
        return toDto(repository.save(category));
    }

    @Override
    @Transactional
    public CategoryDto createCategory(Category category) {
        return toDto(repository.save(category));
    }

    @Override
    @Transactional
    public void deleteCategory(long catId) {
        repository.deleteById(catId);
    }
}
