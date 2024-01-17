package com.explore.exploreWithMe.dto;


import com.explore.exploreWithMe.model.App;

public class MapperApp {
    public static AppDto mapToAppDto(App app){
        return new AppDto(app.getName(), app.getUri(), app.getHit().size());
    }
}
