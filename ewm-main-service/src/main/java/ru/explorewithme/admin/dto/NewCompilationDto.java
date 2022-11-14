package ru.explorewithme.admin.dto;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

@Getter
public class NewCompilationDto {
    private Set<Long> events;
    @Value("false")
    private Boolean pinned;
    private String title;

    @Override
    public String toString() {
        return "NewCompilationDto{" +
                "events=" + events +
                ", pinned=" + pinned +
                ", title='" + title + '\'' +
                '}';
    }
}
