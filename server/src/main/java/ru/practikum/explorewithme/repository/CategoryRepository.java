package ru.practikum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practikum.explorewithme.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
