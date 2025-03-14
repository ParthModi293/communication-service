package com.clapcle.communication.common;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {EnumValueValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumCommunication {

    enum PRIORITY {
        HIGH("1"),
        MEDIUM("2"),
        LOW("3");

        private final String value;

        PRIORITY(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    enum STATUS{
        SUCCESS("SUCCESS"),
        FAILED("FAILED");

        private final String value;
        STATUS(String value) {this.value=value;}
        public String getValue() {return value;}
    }

    String message() default "Invalid value!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends java.lang.Enum<?>> enumClass();

    boolean ignoreCase() default false;
}
