package ru.practikum.explorewithme.dto.in;


import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@Builder
public class UpdateEventRequest {

    @NotNull(message = "id категории не может быть пустым")
    private Long eventId;

    @Nullable
    @Length(min = 20, max = 2000, message = "Краткое описание не менее 20 и не более 2000 символов")
    private String annotation;

    private Long category;

    @Nullable
    @Length(min = 20, max = 7000, message = "Полное описание не менее 20 и не более 7000 символов")
    private String description;

    private String eventDate;

    private Boolean paid;

    @PositiveOrZero
    private Integer participantLimit;

    @Nullable
    @Length(min = 3, max = 120, message = "Заголовок не менее 3 и не более 120 символов")
    private String title;
}
