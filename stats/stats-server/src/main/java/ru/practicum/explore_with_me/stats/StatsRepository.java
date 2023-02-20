package ru.practicum.explore_with_me.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explore_with_me.dto.Stat;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface StatsRepository extends JpaRepository<Stat, Integer> {
    List<Stat> findByTimestampBetweenOrderByAppAscUriAsc(LocalDateTime start, LocalDateTime end);

    List<Stat> findByTimestampBetweenAndUriInOrderByAppAscUriAsc(LocalDateTime start, LocalDateTime end,
                                                                 Collection<String> uri);
}
