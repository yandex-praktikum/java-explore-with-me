package ru.practicum.explore_with_me.ewm_main_service.event.model;

public enum EventStateEnum {
    PENDING,
    PUBLISHED,
    CANCELED,
    REJECT;

    public static EventStateEnum of(String state) {
        for (EventStateEnum command : values()) {
            if (command.name().equalsIgnoreCase(state)) {
                return command;
            }
        }
        return EventStateEnum.PENDING;
    }

    public static EventStateEnum[] of(String[] states) {
        if (states != null) {
            EventStateEnum[] result = new EventStateEnum[states.length];
            for (int i = 0; i < states.length; i++) {
                result[i] = EventStateEnum.of(states[i]);
            }
            return result;
        } else {
            return new EventStateEnum[0];
        }
    }
}
