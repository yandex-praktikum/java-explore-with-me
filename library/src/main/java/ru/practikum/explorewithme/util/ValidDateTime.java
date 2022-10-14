package ru.practikum.explorewithme.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class ValidDateTime {

    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    private ValidDateTime() {
    }

    public static LocalDateTime getDateTime(String inDateTime) {
        if (inDateTime == null) return null;
        try {
            return LocalDateTime.parse(inDateTime, DateTimeFormatter.ofPattern(PATTERN));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Время введено некорректно");
        }
    }

    public static void checkStartTimeOfEvent(String inDateTime, long hours) {
        if (inDateTime == null) return;
        LocalDateTime time = getDateTime(inDateTime);
        if (time.isBefore(LocalDateTime.now().plusHours(hours))) {
            throw new IllegalArgumentException("Дата и время на которые намечено событие не может быть раньше, " +
                    "чем через " + hours + " ч от текущего момента");
        }
    }

    public static boolean isDateTime(Optional<String> inDateTime) {
        inDateTime.ifPresent(ValidDateTime::getDateTime);
        return true;
    }

    public static void checkRangeTime(Optional<String> inStartTime, Optional<String> inEndTime) {
        LocalDateTime start;
        LocalDateTime end;
        if (inStartTime.isPresent() && inEndTime.isPresent()) {
            start = getDateTime(inStartTime.get());
            end = getDateTime(inEndTime.get());
            if (end.isBefore(start)) {
                throw new IllegalArgumentException("Start time > End time");
            }
        }
    }

    public static String dateToString(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern(PATTERN));
    }
}
