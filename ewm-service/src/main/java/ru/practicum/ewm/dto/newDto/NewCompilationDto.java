package ru.practicum.ewm.dto.newDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {
    private Set<Long> events;
    private Boolean pinned;

    @NotNull
    @Length(min = 3, max = 120)
    private String title;
    private LocalDateTime created = LocalDateTime.now();

}
