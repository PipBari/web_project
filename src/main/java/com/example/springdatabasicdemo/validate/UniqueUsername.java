package com.example.springdatabasicdemo.validate;

import com.example.springdatabasicdemo.validate.validator.UsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
    String message() default "Имя пользователя уже занято";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
