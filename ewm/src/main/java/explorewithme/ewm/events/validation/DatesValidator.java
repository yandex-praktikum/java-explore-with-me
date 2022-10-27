package explorewithme.ewm.events.validation;

import lombok.Data;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
@Data
public class DatesValidator implements ConstraintValidator<ValidDates, LocalDateTime> {

    private int hours;

    @Override
    public boolean isValid(LocalDateTime eventDate, ConstraintValidatorContext context) {

        if (eventDate == null
        ) {
            return false;
        }

        return (eventDate.isAfter(LocalDateTime.now().plusHours(hours)));
    }
}
