package explorewithme.ewm.requests;

import explorewithme.ewm.requests.dto.ParticipationRequestDto;
import explorewithme.ewm.requests.services.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class RequestController {

    //Users manage own requests to events
    private final RequestService requestService;


    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> getRequestByUser(@PathVariable long userId){
        log.debug("Get request to private user api call for requests");
        return requestService.getRequestsByUser(userId);
    }

    @PostMapping("/{userId}/requests")
    public ParticipationRequestDto createRequest(@PathVariable long userId,
                                                @RequestParam long eventId){
        log.debug("Post request to private user api to post request for event " + eventId);
        return requestService.createRequest(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable long userId,
                                                @PathVariable long requestId){
        log.debug("Patch request to private user api to cancel own request for the event, req no." + requestId);
        return requestService.cancelRequest(userId, requestId);
    }

}
