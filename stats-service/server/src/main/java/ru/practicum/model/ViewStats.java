package ru.practicum.model;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewStats {

    private String app; // Название сервиса
    private String uri; // URI сервиса
    private Long hits; // Количество просмотров
}
