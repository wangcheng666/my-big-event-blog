package com.wc.anno;

import com.wc.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotEmpty;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = {StateValidation.class}
)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface State {
    String message() default "state参数值只能是已发布或草稿";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
