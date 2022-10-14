package ru.practikum.explorewithme.dto.in;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class NewEventDto {

    @NotNull(message = "Краткое описание не может быть пустым")
    @Length(min = 20, max = 2000, message = "Краткое описание не менее 20 и не более 2000 символов")
    private String annotation;

    @NotNull(message = "id категории не может быть пустым")
    private Long category;

    @NotNull(message = "Полное описание не может быть пусты")
    @Length(min = 20, max = 7000, message = "Полное описание не менее 20 и не более 7000 символов")
    private String description;

    @NotBlank(message = "Время события должно быть указано")
    @NotNull(message = "Время события должно быть указано")
    private String eventDate;

    @NotNull(message = "Координаты должны быть указаны")
    private Location location;

    private boolean paid;

    private int participantLimit;

    private boolean requestModeration;

    @NotNull(message = "Заголовок не может быть пуытм")
    @Length(min = 3, max = 120, message = "Заголовок не менее 3 и не более 120 символов")
    private String title;
}
