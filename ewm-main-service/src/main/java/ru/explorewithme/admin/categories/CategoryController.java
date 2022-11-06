package ru.explorewithme.admin.categories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.admin.dto.CategoryDto;
import ru.explorewithme.admin.dto.NewCategoryDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping(path = "/admin/categories")
@Slf4j
@Validated
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public CategoryDto addCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.info("Creating new category: {}", newCategoryDto);
        return categoryService.addCategory(newCategoryDto);
    }

    @DeleteMapping("/{catId}")
    public void deleteUser(@Positive(message = "id can't be < or = 0") @PathVariable Long catId) {
        log.info("Deleting category with id={}", catId);
        categoryService.deleteCategory(catId);
    }

    @PatchMapping
    public CategoryDto changeCategory(@RequestBody @Valid CategoryDto categoryDto) {
        log.info("Changing existing category to {}", categoryDto);
        return categoryService.changeCategory(categoryDto);
    }
}
