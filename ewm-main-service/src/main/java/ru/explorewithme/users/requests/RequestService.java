package ru.explorewithme.users.requests;

import org.springframework.stereotype.Service;
import ru.explorewithme.IdService;
import ru.explorewithme.admin.model.User;
import ru.explorewithme.admin.users.UserRepository;
import ru.explorewithme.users.dto.ParticipationRequestDto;
import ru.explorewithme.users.events.EventRepository;
import ru.explorewithme.users.model.Event;
import ru.explorewithme.users.model.Request;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RequestService {
    private RequestRepository requestRepository;
    private UserRepository userRepository;
    private EventRepository eventRepository;
    private IdService idService;

    public RequestService(RequestRepository requestRepository,
                          UserRepository userRepository,
                          EventRepository eventRepository,
                          IdService idService) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.idService = idService;
    }

    public ParticipationRequestDto addRequest(Long userId, Long eventId) {
        User requester = idService.getUserById(userId);
        Event event = idService.getEventById(eventId);

        Request request = Request.builder()
                .id(null)
                .created(LocalDateTime.now())
                .status("PENDING")
                .event(event)
                .requester(requester)
                .build();

        return RequestMapper.toParticipationRequestDto(
                requestRepository.save(request));
    }

    public List<ParticipationRequestDto> getAllRequestOfUser(Long userId) {
        List<Request> requests = requestRepository.findByRequester_Id(userId);
        return RequestMapper.toParticipationRequestDto(requests);
    }

    public ParticipationRequestDto cancelRequestOfUser(Long userId, Long requestId) {
        Request request = idService.getRequestById(requestId);
        request.setStatus("CANCELED");
        requestRepository.save(request);
        return RequestMapper.toParticipationRequestDto(request);
    }
}
