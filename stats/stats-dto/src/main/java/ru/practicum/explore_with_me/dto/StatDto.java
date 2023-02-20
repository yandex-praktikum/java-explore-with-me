package ru.practicum.explore_with_me.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StatDto {
    private int id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
