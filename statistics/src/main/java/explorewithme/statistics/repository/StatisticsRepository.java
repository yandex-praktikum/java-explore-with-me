package explorewithme.statistics.repository;

import explorewithme.statistics.model.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics,Long> {

    @Query(nativeQuery = true, value = "SELECT distinct attributes ->> 'ip' as ips from statistics " +
            "where created_at > :start and created_at < :end and  attributes ->> 'uri' in (:uris);")
    List<Statistics> fullFilterMethod(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("uris")List<String> uris);


    @Query (nativeQuery = true, value = "SELECT * from statistics where created_at > :start and created_at < :end and  attributes ->> 'uri' in (:uris);")
    List<Statistics> notUniqueFilterMethod(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("uris")List<String> uris);


    @Query(nativeQuery = true, value = "SELECT * from statistics " +
            "where created_at > ? and created_at < ?;")
    List<Statistics> notUniqueNoUrisFilterMethod(LocalDateTime start, LocalDateTime end);

    @Query(nativeQuery = true, value = "SELECT distinct attributes ->> 'ip' as ips from statistics " +
            "where created_at > ? and created_at < ?;")
    List<Statistics> UniqueNoUrisFilterMethod(LocalDateTime start, LocalDateTime end);

}
