package ru.explorewithme.categories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.category.CategoryService;
import ru.explorewithme.category.dto.CategoryDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@Slf4j
public class CategoryPublicController {
    private CategoryService categoryService;

    public CategoryPublicController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    List<CategoryDto> getCategories(@RequestParam(defaultValue = "0") @PositiveOrZero  Integer from,
                                   @RequestParam(defaultValue = "10") @Positive  Integer size) {
        log.info("Getting categories, from={}, size={}", from, size);
        return categoryService.getCategories(from, size);
    }

    @GetMapping("/{catId}")
    CategoryDto getCategory(@PathVariable @Positive Long catId) {
        log.info("Getting category with id={}", catId);
        return categoryService.getCategory(catId);
    }
}
