package explorewithme.ewm.requests.services;

import explorewithme.ewm.requests.RequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service // Service to avoid cyclic dependecy with event service
@RequiredArgsConstructor
@Slf4j
public class UtilRequestServiceImpl implements UtilRequestService {

    private final RequestRepository requestRepository;

    @Override
    public int getCountOfApproveRequest(long eventId) {
        log.debug("requesting count of confirmed requests from request repo");
        return requestRepository.countRequestByEventAndStatus(eventId);
    }
}
