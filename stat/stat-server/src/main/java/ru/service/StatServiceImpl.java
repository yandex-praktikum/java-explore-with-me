package ru.service;

import enw.ParamDto;
import enw.ParamHitDto;
import enw.ViewStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.exception.ValidateException;
import ru.mapper.StatMapper;
import ru.repository.StatRepository;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;

    @Transactional
    @Override
    public void hit(ParamHitDto paramHitDto) {
        statRepository.save(StatMapper.toEndpointHit(paramHitDto));
    }

    @Override
    public List<ViewStats> getStat(ParamDto paramDto) {
        if (!paramDto.getEnd().isAfter(paramDto.getStart())) {
            throw new ValidateException("Неправильный диапазон времени");
        }
        List<ViewStats> resultAllStatsList;
        if (paramDto.getUnique()) {
            resultAllStatsList = statRepository.findAllUniqueIpAndTimestampBetweenAndUriIn(
                    paramDto.getStart(),
                    paramDto.getEnd(),
                    paramDto.getUris());
        } else {
            resultAllStatsList = statRepository.findAllByAndTimestampBetweenAndUriIn(
                    paramDto.getStart(),
                    paramDto.getEnd(),
                    paramDto.getUris());
        }
        return resultAllStatsList;
    }
}
