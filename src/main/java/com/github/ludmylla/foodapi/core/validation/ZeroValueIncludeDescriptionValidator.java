package com.github.ludmylla.foodapi.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Locale;

public class ZeroValueIncludeDescriptionValidator implements ConstraintValidator<ZeroValueIncludeDescription, Object> {

    private String valueField;
    private String descriptionField;
    private String descriptionRequired;


    @Override
    public void initialize(ZeroValueIncludeDescription constraintAnnotation) {
       this.valueField = constraintAnnotation.valueField();
       this.descriptionField = constraintAnnotation.descriptionField();
       this.descriptionRequired = constraintAnnotation.descriptionRequired();
    }

    @Override
    public boolean isValid(Object objectValid, ConstraintValidatorContext context) {
        boolean valid = true;

        try {
            BigDecimal value = (BigDecimal) BeanUtils.getPropertyDescriptor(objectValid.getClass(), valueField)
                    .getReadMethod().invoke(objectValid);

            String description = (String) BeanUtils.getPropertyDescriptor(objectValid.getClass(), descriptionField)
                    .getReadMethod().invoke(objectValid);

            if(value != null && BigDecimal.ZERO.compareTo(value) == 0 && description != null){
                valid = description.toLowerCase().contains(this.descriptionRequired.toLowerCase());
            }

            return valid;

        } catch (Exception e) {
            throw  new ValidationException(e);
        }

    }
}
