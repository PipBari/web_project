package com.example.springdatabasicdemo.validate.validator;

import com.example.springdatabasicdemo.repositories.BrandRepository;
import com.example.springdatabasicdemo.validate.UniqueBrandName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class BrandNameValidator implements ConstraintValidator<UniqueBrandName, String> {
    private final BrandRepository brandRepository;
    public BrandNameValidator(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !brandRepository.existsByName(s);
    }
}
