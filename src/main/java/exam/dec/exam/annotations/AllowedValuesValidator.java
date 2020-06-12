package exam.dec.exam.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
//@AllowedValues(allowedValues ={"1", "2", "4"};
// private String value;
public class AllowedValuesValidator implements ConstraintValidator<AllowedValues, String> {
    private String[] values;
    private String message;

    @Override
    public void initialize(AllowedValues constraintAnnotation) {
        values = constraintAnnotation.expectedValues();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String val, ConstraintValidatorContext context) {
        if (message != null) {
            context.disableDefaultConstraintViolation();
        }
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return Arrays.asList(values).contains(val);
    }
}
