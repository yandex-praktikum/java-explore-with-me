package com.explore.exploreWithMe.controller;


import com.explore.exploreWithMe.dto.AppDto;
import com.explore.exploreWithMe.dto.HitDto;
import com.explore.exploreWithMe.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@Controller
@RequiredArgsConstructor
@Validated
@RestController
public class StatisticsController {
    @Autowired
    private StatisticService statisticService;

    @GetMapping("/stats")
    public ResponseEntity<List<AppDto>> getStats(@RequestParam String start, @RequestParam String end,
                                                 @RequestParam(required = false) List<String> uris,
                                                 @RequestParam(defaultValue = "false") String unique) {
        return statisticService.getStats(start, end, uris, Boolean.parseBoolean(unique));
    }

    @PostMapping("/hit")
    public ResponseEntity<Object> addHit(@RequestBody HitDto hit) {
        return statisticService.addHit(hit);
    }
}
