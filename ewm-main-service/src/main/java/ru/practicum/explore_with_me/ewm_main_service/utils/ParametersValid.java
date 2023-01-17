package ru.practicum.explore_with_me.ewm_main_service.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import ru.practicum.explore_with_me.ewm_main_service.exceptions.ArgumentNotValidException;

import java.util.Optional;

@Slf4j
public class ParametersValid {

    public static Optional<PageRequest> pageValidated(Integer from, Integer size) {
        if (from != null && size != null) {
            if (from < 0) {
                log.debug("Row index {} must not be less than zero", from);
                throw new ArgumentNotValidException(ParametersValid.class.getName(),
                        String.format("Row index %s must not be less than zero.", from),
                        "Индекс первой строки в выборке не должен быть меньше нуля.");
            } else if (size < 1) {
                log.debug("Size {} of the page to be returned, must be greater than zero", size);
                throw new ArgumentNotValidException(ParametersValid.class.getName(),
                        String.format("Size %s of the page to be returned, must be greater than zero.", size),
                        "Количество строк в выборке должно быть больше или равно нулю.");
            } else {
                return Optional.of(PageRequest.of(from / size, size));
            }
        } else {
            return Optional.empty();
        }
    }
}
