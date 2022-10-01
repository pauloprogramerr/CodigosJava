package com.funcional.interfaces;

import java.lang.annotation.*;

@Repeatable(Roles.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})

public @interface Role {
    String value();
}
