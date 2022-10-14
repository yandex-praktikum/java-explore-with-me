package ru.practikum.explorewithme;

import java.util.Optional;

public enum EventsSort {

    // по дате события
    EVENT_DATE,

    //по количеству просмотров
    VIEWS;

    public static Optional<EventsSort> from(String strEventsSort) {
        for (EventsSort es : values()) {
            if(es.name().equalsIgnoreCase(strEventsSort)) {
                return Optional.of(es);
            }
        }
        return Optional.empty();
    }
}
