package explorewithme.ewm.compilation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CompilationRepository extends JpaRepository<Compilation,Long> {


    @Transactional(timeout = 10)
    Compilation save(Compilation compilation);
    @Query(nativeQuery = true, value ="update compilation set pinned= ? where compilation_id =?")
    @Modifying(clearAutomatically = true)
    int setCompilationPinned(boolean pinned, long id);

    @Query
    List<Compilation> getCompilationsByPinnedEquals(boolean pinned, Pageable pageable);

}
