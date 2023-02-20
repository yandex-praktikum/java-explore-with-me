package ru.practicum.explore_with_me.stats;

import org.springframework.stereotype.Component;
import ru.practicum.explore_with_me.dto.Stat;
import ru.practicum.explore_with_me.dto.StatDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class StatsMapper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public static Stat toStat(StatDto statDto) {

        return Stat.builder()
                .id(statDto.getId())
                .app(statDto.getApp())
                .uri(statDto.getUri())
                .ip(statDto.getIp())
                .timestamp(LocalDateTime.parse(statDto.getTimestamp(), formatter))
                .build();
    }

    public static StatDto toStatDto(Stat stat) {
        return new StatDto(stat.getId(), stat.getApp(), stat.getUri(), stat.getIp(), stat.getTimestamp().toString());
    }


}
