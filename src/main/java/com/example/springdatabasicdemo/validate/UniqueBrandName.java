package com.example.springdatabasicdemo.validate;

import com.example.springdatabasicdemo.validate.validator.BrandNameValidator;
import com.example.springdatabasicdemo.validate.validator.ModelNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = BrandNameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueBrandName {
    String message() default "Такая автомобильная марка уже существует";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
