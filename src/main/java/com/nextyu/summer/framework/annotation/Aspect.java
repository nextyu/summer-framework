package com.nextyu.summer.framework.annotation;

import java.lang.annotation.*;

/**
 * 切面注解
 * created on 2017-06-23 16:54
 *
 * @author nextyu
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /**
     * 注解
     */
    Class<? extends Annotation> value();
}
