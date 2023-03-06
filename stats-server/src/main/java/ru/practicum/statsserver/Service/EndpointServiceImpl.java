package ru.practicum.statsserver.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.statsserver.model.Endpoint;
import ru.practicum.statsserver.model.dto.EndpointDtoOutput;
import ru.practicum.statsserver.repository.EndpointRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EndpointServiceImpl implements EndpointService {
    private final EndpointRepository repository;

    @Transactional
    @Override
    public Endpoint create(Endpoint endpoint) {
        return repository.save(endpoint);
    }

    @Override
    public List<EndpointDtoOutput> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        List<EndpointDtoOutput> stats;
        if (uris.isEmpty()) {
            if (unique.equals(true)) {
                stats =  repository.findDistinctByTimestampBetween(start,end);
            } else {
                stats =  repository.findAllByTimestampBetween(start,end);
            }
        } else {
            if (unique.equals(true)) {
                stats =  repository.findDistinctByTimestampBetweenAndUriIn(start,end,uris);
            } else {
                stats =  repository.findAllByTimestampBetweenAndUriIn(start,end,uris);
            }
        }
        return stats;
    }
}
