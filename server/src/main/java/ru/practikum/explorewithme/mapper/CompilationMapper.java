package ru.practikum.explorewithme.mapper;

import ru.practikum.explorewithme.dto.CompilationDto;
import ru.practikum.explorewithme.dto.in.NewCompilationDto;
import ru.practikum.explorewithme.model.Compilation;
import ru.practikum.explorewithme.model.Event;

import java.util.List;

public class CompilationMapper {

    public static CompilationDto toDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.isPinned())
                .title(compilation.getTitle())
                .events(EventMapper.toShortDto(compilation.getEvents()))
                .build();
    }

    public static Compilation toCompilation(NewCompilationDto dto, List<Event> events) {
        return Compilation.builder()
                .pinned(dto.isPinned())
                .title(dto.getTitle())
                .events(events)
                .build();
    }
}
