package ru.practikum.explorewithme.p_admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practikum.explorewithme.dto.in.CategoryDto;
import ru.practikum.explorewithme.dto.in.NewCategoryDto;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(path = "/admin/categories")
public class AdminControllerCategories {

    private final AdminClientCategories clientCategories;

    @PatchMapping
    public ResponseEntity<Object> updateCategory(@RequestBody @Valid CategoryDto dto) {
        log.info("PATCH update category = " + dto);
        return clientCategories.updateCategory(dto);
    }

    @PostMapping
    public ResponseEntity<Object> createCategory(@RequestBody @Valid NewCategoryDto dto) {
        log.info("POST create category = " + dto);
        return clientCategories.createCategory(dto);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable long catId) {
        log.info("DELETE category with catId={}", catId);
        return clientCategories.deleteCategory(catId);
    }
}
