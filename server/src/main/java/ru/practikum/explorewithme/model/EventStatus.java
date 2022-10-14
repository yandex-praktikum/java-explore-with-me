package ru.practikum.explorewithme.model;

import java.util.ArrayList;
import java.util.List;

public enum EventStatus {

    //ожидает
    PENDING,

    //опубликовано
    PUBLISHED,

    //отменено
    CANCELED;

    public static List<EventStatus> eventsStatus(String[] states) {
        List<EventStatus> eventStatuses = new ArrayList<>();
        for (EventStatus es : values()) {
            for (String state : states) {
                if(es.name().equalsIgnoreCase(state)) {
                    eventStatuses.add(es);
                }
            }
        }
        return eventStatuses;
    }
}
