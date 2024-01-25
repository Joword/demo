package org.example.demo.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Joword
 * @date: 2023/8/3 16:45
 * @version: 0.1.0
 * @description: PM2 criteria annotation
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface Criteria {
    String strength() default "";

    String evidence() default "";

    String reference() default "";

    int like() default 0;
}
