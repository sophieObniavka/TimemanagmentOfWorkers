package obniavka.timemanagment.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target( { ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
    String message() default "{USER.PASSWORD.REQUIRED}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
