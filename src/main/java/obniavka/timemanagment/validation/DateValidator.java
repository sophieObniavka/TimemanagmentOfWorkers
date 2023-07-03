package obniavka.timemanagment.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateValidator implements ConstraintValidator<DateConstraint, LocalDate> {
    @Override
    public void initialize(DateConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Override
    public boolean isValid(LocalDate birth, ConstraintValidatorContext context) {
        LocalDate now = LocalDate.now();
        if(birth==null){
            return true;
        }
        return (now.minusYears(18).isAfter(birth) || now.minusYears(18).equals(birth));
    }
}
