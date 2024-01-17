package com.explore.exploreWithMe.service;


import com.explore.exploreWithMe.dto.AppDto;
import com.explore.exploreWithMe.dto.HitDto;
import com.explore.exploreWithMe.dto.MapperApp;
import com.explore.exploreWithMe.dto.MapperHit;
import com.explore.exploreWithMe.model.App;
import com.explore.exploreWithMe.model.Hit;
import com.explore.exploreWithMe.storage.StatisticsStorage;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Service
public class StatisticService {
    @Autowired
    StatisticsStorage statisticsStorage;

    public ResponseEntity<List<AppDto>> getStats(String start, String end, List<String> uris, boolean unique) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<AppDto> appDtoList;
        if(start.isBlank()){
            start = LocalDateTime.MIN.format(formatter);
        }
        if(end.isBlank()){
            end = LocalDateTime.MAX.format(formatter);
        }
        if(uris==null||uris.get(0).equals("/events")){
            appDtoList = statisticsStorage.getAllStats(List.of("/events"),LocalDateTime.parse(start,formatter),
                    LocalDateTime.parse(end, formatter), unique).stream().map(MapperApp::mapToAppDto)
                    .sorted(Comparator.comparingDouble(AppDto::getHits).reversed()).toList();
        }
        else{
            appDtoList = statisticsStorage.getStatsByUri(uris,LocalDateTime.parse(start,formatter),
                    LocalDateTime.parse(end, formatter), unique).stream().map(MapperApp::mapToAppDto)
                    .sorted(Comparator.comparingDouble(AppDto::getHits).reversed()).toList();
        }
        return new ResponseEntity<>(appDtoList, HttpStatus.OK);
    }

    public ResponseEntity<Object> addHit(HitDto hitDto) {
        if(hitDto.getName()==null){
            hitDto.setName(hitDto.getUri());
        }
        Hit hit = MapperHit.mapToHit(hitDto);
        if(hitDto.getTimestamp()==null){
            hit.setTimestamp(LocalDateTime.now());
        }
        App app = hit.getApp();
        statisticsStorage.createApp(app);
        return new ResponseEntity<>(statisticsStorage.addHit(hit), HttpStatus.CREATED);
    }
}
