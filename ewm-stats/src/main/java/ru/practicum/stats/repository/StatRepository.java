package ru.practicum.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.stats.model.Hit;

public interface StatRepository extends JpaRepository<Hit, Long> {
    Integer countByUri(String uri);
}
