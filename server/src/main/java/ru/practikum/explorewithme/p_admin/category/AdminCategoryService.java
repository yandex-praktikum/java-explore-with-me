package ru.practikum.explorewithme.p_admin.category;

import ru.practikum.explorewithme.dto.CategoryDto;
import ru.practikum.explorewithme.model.Category;

public interface AdminCategoryService {

    CategoryDto updateCategory(Category category);

    CategoryDto createCategory(Category category);

    void deleteCategory(long catId);
}
