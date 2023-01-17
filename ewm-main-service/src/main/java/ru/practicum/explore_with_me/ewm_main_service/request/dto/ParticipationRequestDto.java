package ru.practicum.explore_with_me.ewm_main_service.request.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParticipationRequestDto {
    @NotNull
    String created;
    @NotNull
    Long event;
    @NotNull
    Long id;
    @NotNull
    Long requester;
    @NotBlank
    String status;
}
