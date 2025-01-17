package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.exception.ValidationException;
import ru.practicum.mapper.EndpointHitMapper;
import ru.practicum.model.EndpointHit;
import ru.practicum.repository.EndpointHitRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@Slf4j
@RequiredArgsConstructor
@Service
public class StatsServiceImpl implements StatsService {

    private final EndpointHitRepository endpointHitRepository;
    private final EndpointHitMapper endpointHitMapper;

    @Override
    public EndpointHitDto hit(EndpointHitDto endpointHitDto) {
        if (endpointHitDto == null) return null;
        endpointHitRepository.save(endpointHitMapper.toEndpointHit(endpointHitDto));
        log.info("Информация сохранена");
        return endpointHitDto;
    }

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime startTime,
                                       LocalDateTime endTime,
                                       String uris,
                                       Boolean unique
    ) {
        log.info("Получена статистика по параметрам: Start({}), End({}), Uris({}), Unique({})",
                startTime, endTime, uris, unique);

        List<String> uriList = Arrays.stream(uris.split("\\s*,\\s*"))
                .filter(str -> str.chars().count() != 0)
                .toList();

        List<ViewStatsDto> listViewStatsDto;

        if (startTime.isAfter(endTime)) {
            throw new ValidationException("Время начала не может быть позже времени окончания");
        }

        if (!CollectionUtils.isEmpty(uriList)) {
            if (unique) {
                listViewStatsDto = endpointHitRepository
                        .findAllStatsByTimestampBetweenAndUriIn(startTime, endTime, uriList)
                        .stream()
                        .filter(distinctByKey(EndpointHit::getIp))
                        .map(hit -> {
                            ViewStatsDto dto = new ViewStatsDto();
                            dto.setApp(hit.getApp());
                            dto.setUri(hit.getUri());
                            dto.setHits(1L);
                            return dto;
                        })
                        .toList();
            } else {
                listViewStatsDto = endpointHitRepository
                        .findAllStatsByTimestampBetweenAndUriIn(startTime, endTime, uriList)
                        .stream()
                        .map(hit -> {
                            ViewStatsDto dto = new ViewStatsDto();
                            dto.setApp(hit.getApp());
                            dto.setUri(hit.getUri());
                            dto.setHits(endpointHitRepository.countByUri(hit.getUri()));
                            return dto;
                        })
                        .filter(distinctByKey(ViewStatsDto::getUri))
                        .sorted(Comparator.comparing(ViewStatsDto::getHits).reversed())
                        .toList();
            }
        } else {
            listViewStatsDto = endpointHitRepository
                    .findAllStatsByTimestampBetween(startTime, endTime)
                    .stream()
                    .map(endpointHitMapper::toViewStatsDto)
                    .toList();
        }

        return listViewStatsDto;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

}
