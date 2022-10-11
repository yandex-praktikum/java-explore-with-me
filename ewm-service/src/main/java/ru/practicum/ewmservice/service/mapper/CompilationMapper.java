package ru.practicum.ewmservice.service.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.dto.CompilationDto;
import ru.practicum.ewmservice.dto.newDto.NewCompilationDto;
import ru.practicum.ewmservice.model.Compilation;

@Service
@AllArgsConstructor
public class CompilationMapper {
    private EventMapper eventMapper;

    public Compilation toCompilationFromNewDto(NewCompilationDto dto) {
        Compilation comp = new Compilation();
        comp.setPinned(dto.getPinned());
        comp.setTitle(dto.getTitle());
        comp.setCreated(dto.getCreated());
        return comp;
    }

    public CompilationDto toCompilationDto(Compilation comp) {
        CompilationDto dto = new CompilationDto();
        dto.setId(comp.getId());
        dto.setPinned(comp.getPinned());
        dto.setTitle(comp.getTitle());

        if (comp.getEvents() != null && comp.getEvents().size() > 0) {
            dto.setEvents(eventMapper.getEventShortDtoList(comp.getEvents()));
        }

        return dto;
    }
}
