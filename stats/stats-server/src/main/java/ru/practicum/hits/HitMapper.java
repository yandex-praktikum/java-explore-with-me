package ru.practicum.hit;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.HitRequest;
import ru.practicum.HitsDto;
import ru.practicum.stats.Stat;
import ru.practicum.StatsDto;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface HitMapper {

    @Mapping(target = "timestamp", source = "timestamp", dateFormat = "yyyy-MM-dd HH:mm:ss")
    HitsDto toDto(Hit hit);

    @Mapping(target = "timestamp", source = "timestamp", dateFormat = "yyyy-MM-dd HH:mm:ss")
    Hit toEntity(HitRequest request);

    StatsDto toStatDto(Stat stat);

    List<StatsDto> toStatDto(List<Stat> stats);
}