package ru.practicum.explore_with_me.ewm_main_service.event.model;

public enum EventSortEnum {

    EVENT_DATE,
    VIEWS,
    RATING;

    public static EventSortEnum of(String sort) {
        for (EventSortEnum command : values()) {
            if (command.name().equalsIgnoreCase(sort)) {
                return command;
            }
        }
        return EventSortEnum.VIEWS;
    }
}
