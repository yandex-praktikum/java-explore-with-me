package ru.explorewithme.admin.compilations;

import ru.explorewithme.admin.dto.CompilationDto;
import ru.explorewithme.admin.dto.NewCompilationDto;
import ru.explorewithme.admin.model.Compilation;
import ru.explorewithme.users.dto.EventShortDto;
import ru.explorewithme.users.events.EventMapper;
import ru.explorewithme.users.model.Event;

import java.util.List;
import java.util.stream.Collectors;

public class CompilationMapper {

    public static Compilation toCompilation(NewCompilationDto newCompilationDto, List<Event> events) {
        return Compilation
                .builder()
                .id(null)
                .title(newCompilationDto.getTitle())
                .pinned(newCompilationDto.getPinned())
                .events(events)
                .build();
    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return CompilationDto
                .builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .events(
                        compilation.getEvents().stream().map(EventMapper::toEventShortDto).collect(Collectors.toList())
                )
                .build();
    }

}
