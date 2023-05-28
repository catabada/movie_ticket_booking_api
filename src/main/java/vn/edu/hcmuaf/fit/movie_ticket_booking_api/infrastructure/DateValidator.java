package vn.edu.hcmuaf.fit.movie_ticket_booking_api.infrastructure;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Date;

public class DateValidator implements ConstraintValidator<DateConstant, Date> {

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        return value != null && value.getTime() < new Date().getTime();
    }
}
