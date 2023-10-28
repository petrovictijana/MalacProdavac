package server.server.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import server.server.validation.UsernameValidation;

import java.util.regex.Pattern;

public class UsernameValidator implements ConstraintValidator<UsernameValidation, String> {
    private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9]*$";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        }

        return Pattern.matches(PASSWORD_PATTERN, s);
    }
}
