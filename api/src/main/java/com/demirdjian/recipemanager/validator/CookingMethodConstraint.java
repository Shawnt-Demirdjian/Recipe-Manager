package com.demirdjian.recipemanager.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = CookingMethodValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CookingMethodConstraint {

	/**
	 * Returns the error message. Defaults to "Invalid CookingMethod".
	 * 
	 * @return String
	 */
	String message() default "Invalid Cooking Method.";

	/**
	 * Defines targeted groups.
	 * 
	 * @return Class<?>[]
	 */
	Class<?>[] groups() default {};

	/**
	 * For extensibility purposes.
	 * 
	 * @return Class<? extends Payload>
	 */
	Class<? extends Payload>[] payload() default {};
}
