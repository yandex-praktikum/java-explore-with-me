package ru.practicum.explore_with_me.ewm_main_service.exceptions;

import ru.practicum.explore_with_me.ewm_main_service.utils.RoleEnum;

public class AccessForbiddenException extends BaseException {

    public AccessForbiddenException(String field, String message, String reason) {
        super(field, message, reason);
    }

    public AccessForbiddenException(Object fieldClass, String role) {
        this(fieldClass, RoleEnum.of(role));
    }

    public AccessForbiddenException(Object fieldClass, RoleEnum role) {
        super(fieldClass.getClass().getName(),
                String.format("Role %s has no access.", role.name()),
                "С заданной ролью доступ к ресурсу отсутствует.");
    }

    public AccessForbiddenException(Object fieldClass, Long userId) {
        super(fieldClass.getClass().getName(),
                String.format("User %s has no access.", userId),
                "У заданного пользователя доступ к ресурсу отсутствует.");
    }
}
