package ru.practicum.explore_with_me.ewm_main_service.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.ewm_main_service.category.dto.CategoryDto;
import ru.practicum.explore_with_me.ewm_main_service.category.dto.NewCategoryDto;
import ru.practicum.explore_with_me.ewm_main_service.category.service.CategoryService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private static final String HEADER_USER_ROLE = "X-Explore-With-Me-User-Role";
    private final CategoryService categoryService;


    @PostMapping
    public CategoryDto createCategory(@Valid @RequestBody NewCategoryDto categoryDto,
                                      @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Создание категории, name={}", categoryDto.getName());
        return categoryService.createCategory(categoryDto, role);
    }

    @GetMapping()
    public List<CategoryDto> findCategories(@RequestParam(required = false) Integer from,
                                            @RequestParam(required = false) Integer size) {
        log.info("Поиск категорий, from={}, size={}", from, size);
        return categoryService.findCategoriesToDto(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategory(@PathVariable Long catId) {
        log.info("Получение категории, Id={}", catId);
        return categoryService.getCategoryToDto(catId);
    }

    @PatchMapping
    public CategoryDto editCategory(@Valid @RequestBody CategoryDto categoryDto,
                                    @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Правка категории, name={}", categoryDto.getName());
        return categoryService.editCategoryToDto(categoryDto, role);
    }

    @DeleteMapping("/{catId}")
    public void removeCategory(@PathVariable Long catId,
                               @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Удаление категории {}", catId);
        categoryService.removeCategory(catId, role);
    }
}
