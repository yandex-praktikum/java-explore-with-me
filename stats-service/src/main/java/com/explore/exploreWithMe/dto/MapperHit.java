package com.explore.exploreWithMe.dto;




import com.explore.exploreWithMe.model.App;
import com.explore.exploreWithMe.model.Hit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MapperHit {
    public static App mapToApp(HitDto hitDto){
        return new App(hitDto.getUri(), hitDto.getName(), null);
    }
    public static Hit mapToHit(HitDto hitDto){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new Hit(null,mapToApp(hitDto), hitDto.getIp(), LocalDateTime.parse(hitDto.getTimestamp(), formatter));
    }
}
