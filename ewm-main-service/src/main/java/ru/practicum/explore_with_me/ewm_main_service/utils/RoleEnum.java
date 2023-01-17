package ru.practicum.explore_with_me.ewm_main_service.utils;

public enum RoleEnum {
    PUBLIC,
    USER,
    ADMINISTRATOR;

    public static RoleEnum of(String state) {
        for (RoleEnum command : values()) {
            if (command.name().equalsIgnoreCase(state)) {
                return command;
            }
        }
        return RoleEnum.USER;
    }
}
