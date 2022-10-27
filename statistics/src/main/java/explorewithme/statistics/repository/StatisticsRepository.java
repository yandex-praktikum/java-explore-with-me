package explorewithme.statistics.repository;

import explorewithme.statistics.model.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics,Long> {

    @Query(nativeQuery = true, value = "SELECT distinct attributes ->> 'ip' as ips from statistics " +
            "where created_at > ? and created_at < ? and  attributes ->> 'uri' in ?;")
    List<Statistics> fullFilterMethod(LocalDateTime start, LocalDateTime end, String[] uris);

    @Query(nativeQuery = true, value = "SELECT * from statistics " +
            "where created_at > ? and created_at < ? and  attributes ->> 'uri' in ?;")
    List<Statistics> notUniqueFilterMethod(LocalDateTime start, LocalDateTime end, String[] uris);

    @Query(nativeQuery = true, value = "SELECT * from statistics " +
            "where created_at > ? and created_at < ?;")
    List<Statistics> notUniqueNoUrisFilterMethod(LocalDateTime start, LocalDateTime end);

    @Query(nativeQuery = true, value = "SELECT distinct attributes ->> 'ip' as ips from statistics " +
            "where created_at > ? and created_at < ?;")
    List<Statistics> UniqueNoUrisFilterMethod(LocalDateTime start, LocalDateTime end);

    @Query(nativeQuery = true, value = "INSERT INTO statistics (app, attributes,created_at) values ( ?, ?, ?)")
    Statistics create(String app, String json, LocalDateTime time);
}
