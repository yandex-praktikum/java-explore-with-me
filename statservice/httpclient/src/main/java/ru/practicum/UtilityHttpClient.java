package ru.practicum;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@UtilityClass
public class UtilityHttpClient {
    public static HashMap<String, String> trimLocalDateTime(LocalDateTime start, LocalDateTime end) {
        if (start != null && end != null) {
            HashMap<String, String> dateTime = new HashMap<>();
            DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");

            String startDate = start.toLocalDate().format(formatterDate);
            String startTime = start.toLocalTime().format(formatterTime);
            String endDate = end.toLocalDate().format(formatterDate);
            String endTime = end.toLocalTime().format(formatterTime);

            dateTime.put("startDate", startDate);
            dateTime.put("startTime", startTime);
            dateTime.put("endDate", endDate);
            dateTime.put("endTime", endTime);
            return dateTime;
        } else  {
            throw new RuntimeException("start or end should not be null");
        }
    }
}
