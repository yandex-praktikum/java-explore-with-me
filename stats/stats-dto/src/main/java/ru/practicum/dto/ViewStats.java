package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
@AllArgsConstructor
public class ViewStats {
    String app;
    String uri;
    Long hits;
}
