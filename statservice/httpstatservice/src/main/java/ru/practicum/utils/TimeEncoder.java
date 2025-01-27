package ru.practicum.utils;

import lombok.experimental.UtilityClass;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class TimeEncoder {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public LocalDateTime decodeToLclDt(String input) {
        return LocalDateTime.parse(URLDecoder.decode(input, StandardCharsets.UTF_8), formatter);
    }
}
