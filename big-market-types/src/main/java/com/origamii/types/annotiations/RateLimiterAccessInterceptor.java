package com.origamii.types.annotiations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RateLimiterAccessInterceptor {

    String key() default "all";

    double permitsPerSecond();

    double blackListCount() default 0;

    String fallbackMethod();


}
