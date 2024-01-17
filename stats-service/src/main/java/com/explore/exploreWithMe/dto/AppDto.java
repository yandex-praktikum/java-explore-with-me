package com.explore.exploreWithMe.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppDto {
    private String uri;
    private String app;
    private Integer hits;
}