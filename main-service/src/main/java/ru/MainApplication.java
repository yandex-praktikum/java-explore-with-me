package ru;

import client.StatClient;
import enw.ParamDto;
import enw.ParamHitDto;
import enw.ViewStats;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = {"client", "ru"})
public class MainApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class, args);
        StatClient statClient = context.getBean(StatClient.class);

        ParamHitDto hitDto = new ParamHitDto();
        hitDto.setApp("ewm-main-service");
        hitDto.setUri("/events");
        hitDto.setIp("121.0.0.1");
        hitDto.setTimestamp(LocalDateTime.now());

        ParamHitDto hitDto2 = new ParamHitDto();
        hitDto2.setApp(hitDto.getApp());
        hitDto2.setUri("/test");
        hitDto2.setIp(hitDto.getIp());
        hitDto2.setTimestamp(hitDto.getTimestamp());

        statClient.hit(hitDto);
        statClient.hit(hitDto2);

        ParamDto paramDto = new ParamDto(
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1),
                List.of(hitDto.getUri(), hitDto2.getUri()),
                false);

        List<ViewStats> result = statClient.getStat(paramDto);
    }
}