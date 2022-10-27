package explorewithme.ewm.events.repository;

import explorewithme.ewm.events.State;
import explorewithme.ewm.events.model.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface EventRepository extends JpaRepository<Event,Long>, JpaSpecificationExecutor<Event> {

    @Query
    List<Event> findEventsByInitiator(long userId, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update Event e set e.state = ?1, e.requestModeration = ?2 where e.id =?3")
    int updateEventStatus(State state, boolean moderation, long eventId);

    @Modifying(clearAutomatically = true)
    @Query(value = "update Event e set e.views = ?1 where e.id =?2")
    int updateEventViews(long views, long eventId);

}
