package ru.practikum.explorewithme.dto.in;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class NewCategoryDto {

    @NotNull
    @NotBlank
    private String name;
}
