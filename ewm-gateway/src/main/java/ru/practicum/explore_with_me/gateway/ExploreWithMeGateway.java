package ru.practicum.explore_with_me.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ExploreWithMeGateway {
    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(10);
        SpringApplication.run(ExploreWithMeGateway.class, args);
    }
}