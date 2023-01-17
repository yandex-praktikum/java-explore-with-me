package ru.practicum.explore_with_me.ewm_main_service.compilation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore_with_me.ewm_main_service.compilation.model.Compilation;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    List<Compilation> findAllByPinned(Boolean pinned);

    Page<Compilation> findAllByPinned(Boolean pinned, Pageable pageable);

}
