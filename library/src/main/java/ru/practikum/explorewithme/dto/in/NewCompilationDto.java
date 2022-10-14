package ru.practikum.explorewithme.dto.in;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class NewCompilationDto {

    private Set<Long> events;

    private boolean pinned;

    @NotBlank
    private String title;
}
