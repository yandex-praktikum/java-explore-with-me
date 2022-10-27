package explorewithme.ewm.compilation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CompEventRepository extends JpaRepository<CompilationEvent, Long> {

    @Transactional(timeout = 10)
    CompilationEvent save(CompilationEvent compilation);

    @Query
    int countCompilationEventByIdEqualsAndAndEventEquals(long compId, long eventId);

    @Query
    int countCompilationEventByIdEquals(long compId);

    @Query
    CompilationEvent getCompilationEventByIdEqualsAndAndEventEquals(long compId, long eventId);

    @Query ("select event from CompilationEvent where id = ?1")
    List<Integer> getEventsByIdEquals(long compId);


}
