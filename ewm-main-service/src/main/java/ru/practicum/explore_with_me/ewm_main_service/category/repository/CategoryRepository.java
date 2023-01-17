package ru.practicum.explore_with_me.ewm_main_service.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore_with_me.ewm_main_service.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Boolean existsCategoriesByName(String name);
}
