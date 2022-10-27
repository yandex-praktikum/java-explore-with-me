package explorewithme.ewm.requests.services;

import explorewithme.ewm.events.service.EventService;
import explorewithme.ewm.exception.ArgumentException;
import explorewithme.ewm.exception.ConflictException;
import explorewithme.ewm.exception.NotFoundException;
import explorewithme.ewm.requests.RequestMapper;
import explorewithme.ewm.requests.RequestRepository;
import explorewithme.ewm.requests.dto.ParticipationRequestDto;
import explorewithme.ewm.requests.model.Request;
import explorewithme.ewm.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static explorewithme.ewm.requests.Status.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {

    private final UserService userService;
    private final RequestRepository repository;

    private final EventService eventService;



    @Override
    public List<ParticipationRequestDto> getRequestsByUser(long userId) {
        userService.checkId(userId);
        log.debug("Asking list of requests by user from repo");
       return repository.getRequestsByRequester(userId).stream()
                .map(RequestMapper::fromRequest)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto createRequest(long userId, long eventId) {
        userService.checkId(userId);
        int limit = eventService.checkRequestIsAllowed(userId,eventId);
        if (limit != 0){
            if (repository.countRequestByEventAndStatus(eventId) >= limit) {
                log.debug("Event limit is over");
                throw new ConflictException("Event limit is over");
            }
        }
        if (repository.getRequestsByRequesterAndEvent(userId,eventId).size()>0){
            log.debug("For each event one user cannot submit more than 1 request");
            throw new ConflictException("Cannot submit more than 1 request");
        }
        Request request = repository.save(new Request(userId,eventId));
        return  RequestMapper.fromRequest(request);
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(long userId, long requestId) {
        userService.checkId(userId);
        checkId(requestId);
        if (repository.findById(requestId).get().getRequester() != userId){
            log.debug("Request doesn't belong to the user");
            throw new ConflictException("Request doesn't belong to the user");
        } else if (repository.getReferenceById(requestId).getStatus() == CANCELED) {
            log.debug("Status of the request is already canceled");
            throw new ConflictException("Status of the request is already canceled");
        }

        Request request = repository.getReferenceById(requestId);
        request.setStatus(CANCELED);
        log.debug("Saving request with status canceled");
        Request requestToReturn = repository.save(request);
        return RequestMapper.fromRequest(requestToReturn);
    }


    @Override
    public void checkId(long id) {
        if (repository.findById(id).isEmpty()){
            log.debug("Request with id "+ id + " not found");
            throw new NotFoundException("Request with id "+ id + " not found");
        }
    }

    //Private API methods

    @Override
    public List<ParticipationRequestDto> getRequestsByEvent(long userId, long eventId) {
        userService.checkId(userId);
        eventService.checkEventId(eventId);
        if (!eventService.checkOwnership(userId, eventId)){
            log.debug("Only owner can view requests by event");
            throw new ConflictException("Only owner can view requests by event");
        }
        log.debug("Asking repo for requests to event");
        return repository.getRequestsByEvent(eventId).stream()
                .map(RequestMapper::fromRequest)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto confirmRequest(long userId, long eventId, long reqId) {
        userService.checkId(userId);
        eventService.checkEventId(eventId);
        checkId(reqId);
        if (eventService.getEventById(eventId).getInitiator().getId() != userId) {
            log.debug("Only owner can confirm request to event");
            throw new ConflictException("Only owner can confirm request to event");
        }
        if (repository.getReferenceById(reqId).getStatus() == CANCELED &&
                repository.getReferenceById(reqId).getStatus() == CONFIRMED)
         {
             log.debug("Status of the request is either canceled by requester or already approved, cannot change status");
            throw new ConflictException("Status of the request is either canceled by requester or already approved," +
                    " cannot change status");
        } else if (eventService.getEventById(eventId).getParticipantLimit() != 0){
            if (repository.countRequestByEventAndStatus(eventId) >=
                    eventService.getEventById(eventId).getParticipantLimit()) {
                log.debug("Event limit is over");
                throw new ArgumentException("Event limit is over");
            }
        }
        Request request = repository.getReferenceById(reqId);
        request.setStatus(CONFIRMED);
        log.debug("Saving request with status confirmed");
        Request requestToReturn = repository.save(request);
        return RequestMapper.fromRequest(requestToReturn);
    }

    @Override
    @Transactional
    public ParticipationRequestDto rejectRequest(long userId, long eventId, long reqId) {
        userService.checkId(userId);
        eventService.checkEventId(eventId);
        checkId(reqId);
        if(!eventService.checkOwnership(userId,eventId)){
            log.debug("Only owner can confirm request to event");
            throw new ConflictException("Only owner can confirm request to event");
        }
        if (repository.getReferenceById(reqId).getStatus() == CANCELED &&
                repository.getReferenceById(reqId).getStatus() == REJECTED)
        {
            log.debug("Status of the request is either canceled by requester or already declined," +
                    " cannot change status");
            throw new ConflictException("Status of the request is either canceled by requester or already declined," +
                    " cannot change status");
        }
        Request request = repository.getReferenceById(reqId);
        request.setStatus(REJECTED);
        log.debug("Saving request with status rejected");
        Request requestToReturn = repository.save(request);
        return RequestMapper.fromRequest(requestToReturn);
    }

}
