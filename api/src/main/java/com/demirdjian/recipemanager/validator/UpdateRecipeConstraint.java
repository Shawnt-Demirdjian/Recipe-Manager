package com.demirdjian.recipemanager.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = UpdateRecipeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UpdateRecipeConstraint {

	/**
	 * Returns the error message. Defaults to "Must include at least one property to
	 * update.".
	 * 
	 * @return String
	 */
	String message() default "Must include at least one property to update.";

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
