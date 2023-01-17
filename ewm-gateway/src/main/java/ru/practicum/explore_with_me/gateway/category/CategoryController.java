package ru.practicum.explore_with_me.gateway.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.gateway.client.RoleEnum;

@Slf4j
@RestController
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
@Validated
public class CategoryController {
    private final CategoryClient categoryClient;

    @GetMapping
    public ResponseEntity<Object> findCategories(@RequestParam(defaultValue = "0") int from,
                                                 @RequestParam(defaultValue = "10") int size) {
        log.info("Поиск категорий, from={}, size={}", from, size);
        return categoryClient.findCategories(from, size, RoleEnum.PUBLIC);
    }

    @GetMapping("/{catId}")
    public ResponseEntity<Object> findById(@PathVariable Long catId) {
        log.info("Получение категории по Id, catId={}", catId);
        return categoryClient.getById(catId, RoleEnum.PUBLIC);
    }
}
