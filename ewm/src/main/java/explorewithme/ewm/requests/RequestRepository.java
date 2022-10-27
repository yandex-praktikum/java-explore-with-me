package explorewithme.ewm.requests;

import explorewithme.ewm.requests.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RequestRepository extends JpaRepository<Request,Long> {

    @Query
    List<Request> getRequestsByRequester(long userId);

    @Query
    List<Request> getRequestsByRequesterAndEvent(long userId, long eventId);

    @Query (nativeQuery = true, value = "select count(request_id) from requests where event_id = ? and status='CONFIRMED'")
    int countRequestByEventAndStatus(long eventId);

    @Query
    List<Request> getRequestsByEvent(long eventId);

}
