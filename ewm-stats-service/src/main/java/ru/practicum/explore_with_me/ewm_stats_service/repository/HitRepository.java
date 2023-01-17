package ru.practicum.explore_with_me.ewm_stats_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explore_with_me.ewm_stats_service.model.Hit;

@Repository
public interface HitRepository extends JpaRepository<Hit, Long> {

}
