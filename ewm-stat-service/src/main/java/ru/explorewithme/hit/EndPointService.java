package ru.explorewithme.hit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EndPointService {
    private EndPointRepository endPointRepository;

    public EndPointService(EndPointRepository endPointRepository) {
        this.endPointRepository = endPointRepository;
    }

    public void addEndPoint(EndPointHit endPointHit) {
        endPointHit = endPointRepository.save(endPointHit);
        log.info("Added endpoint={}", endPointHit);
    }
}

