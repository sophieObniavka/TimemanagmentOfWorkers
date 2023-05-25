package obniavka.timemanagment.validation;

import obniavka.timemanagment.domain.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, UserDto> {
    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserDto user, ConstraintValidatorContext context) {
        return (user.getId() != null) ||  (user.getPassword() != null && !user.getPassword().isEmpty());
    }

}
