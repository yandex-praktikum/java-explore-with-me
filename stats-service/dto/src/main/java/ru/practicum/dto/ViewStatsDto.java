package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewStatsDto {

    private String app; // Название сервиса

    private String uri; // URI сервиса

    private Long hits; // Количество просмотров

}
