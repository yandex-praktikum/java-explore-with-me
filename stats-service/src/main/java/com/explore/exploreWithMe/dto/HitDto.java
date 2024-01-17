package com.explore.exploreWithMe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HitDto {
    private String name;
    private String uri;
    private String ip;
    private String timestamp;

}
