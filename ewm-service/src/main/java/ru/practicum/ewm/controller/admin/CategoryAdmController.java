package ru.practicum.ewm.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.newDto.NewCategoryDto;
import ru.practicum.ewm.service.CategoryService;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping(path = "/admin/categories")
@AllArgsConstructor
public class CategoryAdmController {
    private CategoryService service;

    @PatchMapping
    public CategoryDto updateCategory(@RequestBody CategoryDto dto) {
        return service.update(dto);
    }

    @PostMapping
    public CategoryDto createCategory(@RequestBody NewCategoryDto dto) {
        return service.create(dto);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable @Positive long catId) {
        service.delete(catId);
    }
}
