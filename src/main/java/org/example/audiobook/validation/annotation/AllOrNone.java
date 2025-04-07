package org.example.audiobook.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.audiobook.validation.validator.AllOrNoneValidator;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = AllOrNoneValidator.class)
public @interface AllOrNone {
	String[] fields();

	String message() default "These field must be all null or all not null.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
