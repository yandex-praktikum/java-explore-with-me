package ru.practicum;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticViewDto {
    @JsonProperty("app")
    String application;
    @JsonProperty("uri")
    String uri;
    @JsonProperty("hits")
    long hits;
}

