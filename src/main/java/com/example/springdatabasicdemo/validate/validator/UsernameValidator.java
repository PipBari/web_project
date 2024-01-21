package com.example.springdatabasicdemo.validate.validator;

import com.example.springdatabasicdemo.repositories.UserRepository;
import com.example.springdatabasicdemo.validate.UniqueUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    private final UserRepository userRepository;

    public UsernameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepository.existsByUsername(s);
    }
}

