package ru.practikum.explorewithme;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practikum.explorewithme.model.EndpointHit;
import ru.practikum.explorewithme.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class StatServiceImpl implements StatService{

    private final StatRepository repository;

    @Override
    @Transactional
    public EndpointHit saveEndpointHit(EndpointHit endpointHit) {
        return repository.save(endpointHit);
    }

    @Override
    public List<ViewStats> getStat(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
        if (unique) {
            return repository.findAllByUniqueIp(start, end, uris);
        } else {
            return repository.findAllByUniqueUri(start, end, uris);
        }
    }
}
