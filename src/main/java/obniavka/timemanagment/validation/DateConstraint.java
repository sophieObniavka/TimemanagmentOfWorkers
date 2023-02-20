package obniavka.timemanagment.validation;

import java.lang.annotation.*;
import javax.validation.Payload;
import javax.validation.Constraint;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target( { ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateConstraint {
    String message() default "{USER.DATEBIRTH.INVALID}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
