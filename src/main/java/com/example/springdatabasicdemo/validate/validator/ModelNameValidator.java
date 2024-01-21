package com.example.springdatabasicdemo.validate.validator;

import com.example.springdatabasicdemo.repositories.ModelRepository;
import com.example.springdatabasicdemo.validate.UniqueModelName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class ModelNameValidator implements ConstraintValidator<UniqueModelName, String> {
    private final ModelRepository modelRepository;

    public ModelNameValidator(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !modelRepository.existsByName(s);
    }
}
