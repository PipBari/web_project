package com.example.springdatabasicdemo.validate;

import com.example.springdatabasicdemo.validate.validator.ModelNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ModelNameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueModelName {
    String message() default "Такая модель уже существует";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
