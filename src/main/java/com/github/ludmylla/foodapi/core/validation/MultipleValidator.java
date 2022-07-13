package com.github.ludmylla.foodapi.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class MultipleValidator implements ConstraintValidator<Multiple, Number> {

    private int numberMultiple;

    @Override
    public void initialize(Multiple constraintAnnotation) {
        this.numberMultiple = constraintAnnotation.number();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        boolean valid = true;

        if (value != null) {
            var valueDecimal = BigDecimal.valueOf(value.doubleValue());
            var multipleDecimal = BigDecimal.valueOf(this.numberMultiple);
            var remnant = valueDecimal.remainder(multipleDecimal);
            valid = BigDecimal.ZERO.compareTo(remnant) == 0;
        }

        return valid;
    }
}
