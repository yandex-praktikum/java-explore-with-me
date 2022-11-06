package ru.explorewithme.stats;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ViewStats {
    private String app;
    private String uri;
    private Long hits;
}
