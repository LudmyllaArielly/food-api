package com.github.ludmylla.foodapi.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = { ZeroValueIncludeDescriptionValidator.class })
public @interface ZeroValueIncludeDescription {

    String message() default "Description required invalid.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String valueField();

    String descriptionField();

    String descriptionRequired();



}
