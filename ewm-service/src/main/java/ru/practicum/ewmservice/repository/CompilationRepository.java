package ru.practicum.ewmservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import ru.practicum.ewmservice.model.Compilation;


@Component
public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    @Modifying
    @Query(value = "delete from event_compilation where compilation_id = ?1 and event_id = ?2", nativeQuery = true)
    void deleteEventFromComp(long compilationId, long eventId);

    @Modifying
    @Query(value = "update compilations set pinned = ?1 where id = ?2", nativeQuery = true)
    Integer installPin(boolean value, long compId);

    Page<Compilation> findAllByPinned(Boolean pinned, Pageable pageable);
}
