package ru.practicum.ewm.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.ParticipationRequestDto;
import ru.practicum.ewm.service.ParticipationRequestService;

import java.util.List;

@Service
@AllArgsConstructor
public class ParticipationRequestServiceImpl implements ParticipationRequestService {
    @Override
    public List<ParticipationRequestDto> getParticipationRequestsByUserId(long userId) {
        return null;
    }

    @Override
    public ParticipationRequestDto postPartRequest(long userId, long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto cancelMainPartRequest(long userId, long requestId) {
        return null;
    }
}
