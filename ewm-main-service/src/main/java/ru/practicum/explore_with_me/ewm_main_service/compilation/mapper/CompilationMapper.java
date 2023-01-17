package ru.practicum.explore_with_me.ewm_main_service.compilation.mapper;

import org.mapstruct.Mapper;
import ru.practicum.explore_with_me.ewm_main_service.compilation.dto.CompilationDto;
import ru.practicum.explore_with_me.ewm_main_service.compilation.dto.NewCompilationDto;
import ru.practicum.explore_with_me.ewm_main_service.compilation.model.Compilation;
import ru.practicum.explore_with_me.ewm_main_service.event.mapper.EventMapper;

@Mapper(uses = {EventMapper.class})
public interface CompilationMapper {

    CompilationDto toDto(Compilation compilation);

    default Compilation toCompilation(NewCompilationDto dto) {
        if (dto == null) {
            return null;
        }

        Compilation compilation = new Compilation();
        compilation.setTitle(dto.getTitle());
        compilation.setPinned(dto.getPinned() != null ? dto.getPinned() : false);

        return compilation;
    }
}
