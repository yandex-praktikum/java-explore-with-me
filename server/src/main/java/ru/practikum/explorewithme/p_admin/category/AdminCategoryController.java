package ru.practikum.explorewithme.p_admin.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practikum.explorewithme.dto.CategoryDto;
import ru.practikum.explorewithme.model.Category;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(path = "/admin/categories")
public class AdminCategoryController {

    private final AdminCategoryService service;

    @PatchMapping
    public CategoryDto updateCategory(@RequestBody Category category) {
        log.info("SERVER PATCH update category");
        return service.updateCategory(category);
    }

    @PostMapping
    public CategoryDto createCategory(@RequestBody Category category) {
        log.info("SERVER POST create category");
        return service.createCategory(category);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable long catId) {
        log.info("SERVER DELETE category with catId={}", catId);
        service.deleteCategory(catId);
    }
}
