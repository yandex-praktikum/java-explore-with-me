package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.practicum.StatisticEventDto;
import ru.practicum.StatisticViewDto;
import ru.practicum.model.StatisticEvent;
import ru.practicum.repository.StatisticRepository;

import java.time.LocalDateTime;
import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final StatisticRepository statisticRepository;

    @Override
    public StatisticEventDto addEvent(StatisticEventDto eventDto) {
        StatisticEvent event = statisticEventDtoToEventMapper(eventDto);
        return statisticEventToDtoMapper(statisticRepository.save(event));
    }

    @Override
    public List<StatisticViewDto> getEvents(LocalDateTime startTime, LocalDateTime endTime, List<String> uris,
                                            boolean unique) {
        List<StatisticViewDto> events;
        if (uris != null && !uris.isEmpty()) {
            if (unique) {
                events = statisticRepository.findByDateAndByUriAndUniqueIp(startTime, endTime, uris);
            } else {
                events = statisticRepository.findByDateAndByUri(startTime, endTime, uris);
            }
        } else {
            if (unique) {
                events = statisticRepository.findByDateAndUniqueIp(startTime, endTime);
            } else {
                events = statisticRepository.findByDate(startTime, endTime);
            }
        }
        return events;
    }

    public StatisticEventDto statisticEventToDtoMapper(StatisticEvent event) {
        return StatisticEventDto.builder()
                .id(event.getId())
                .application(event.getApplication())
                .uri(event.getUri())
                .ip(event.getIp())
                .eventTime(event.getEventTime())
                .build();
    }

    public StatisticEvent statisticEventDtoToEventMapper(StatisticEventDto eventDto) {
        return StatisticEvent.builder()
                .application(eventDto.getApplication())
                .uri(eventDto.getUri())
                .ip(eventDto.getIp())
                .eventTime(eventDto.getEventTime())
                .build();
    }
}
