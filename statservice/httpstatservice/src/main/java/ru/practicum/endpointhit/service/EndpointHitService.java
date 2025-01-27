package ru.practicum.endpointhit.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.endpointhit.model.EndpointHit;
import ru.practicum.endpointhit.repository.EndpointHitStorageJpa;

import java.util.List;

@Slf4j
@Service
public class EndpointHitService {
    private final EndpointHitStorageJpa endpointHitStorageJpa;

    @Autowired
    public EndpointHitService(EndpointHitStorageJpa endpointHitStorageJpa) {
        this.endpointHitStorageJpa = endpointHitStorageJpa;
    }

    public List<EndpointHit> getAll() {
        return endpointHitStorageJpa.findAll();
    }

    public EndpointHit create(EndpointHit endpointHit) {
        return endpointHitStorageJpa.save(endpointHit);
    }

}
