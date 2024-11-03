package com.origamii.types.annotiations;

import java.lang.annotation.*;

/**
 * @author Origami
 * @description dynamic config center value annotation
 * @create 2024-11-03 19:51
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface DCCValue {

    String value() default "";



}
