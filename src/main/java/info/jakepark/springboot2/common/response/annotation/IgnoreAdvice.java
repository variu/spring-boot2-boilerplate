package info.jakepark.springboot2.common.response.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreAdvice {
    boolean ignore() default true;
}
