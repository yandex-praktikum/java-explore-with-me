package ru.practikum.explorewithme.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CompilationDto {
    private long id;
    private boolean pinned;
    private String title;
    private List<EventShortDto> events;
}
