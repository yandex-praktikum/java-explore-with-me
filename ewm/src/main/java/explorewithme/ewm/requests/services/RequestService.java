package explorewithme.ewm.requests.services;

import explorewithme.ewm.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    List<ParticipationRequestDto> getRequestsByUser(long userId);

    ParticipationRequestDto createRequest(long userId, long eventId);

    ParticipationRequestDto cancelRequest(long userId, long requestId);

    void checkId(long id);

    List<ParticipationRequestDto> getRequestsByEvent(long userId, long eventId);

    ParticipationRequestDto confirmRequest(long userId, long eventId, long reqId);

    ParticipationRequestDto rejectRequest(long userId, long eventId, long reqId);

 //   int getCountOfApproveRequest(long eventId);
}
