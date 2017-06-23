package com.nextyu.summer.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * created on 2017-06-23 12:26
 *
 * @author nextyu
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    /**
     * 请求路径
     *
     * @return
     */
    String name() default "";

    /**
     * 请求方法
     *
     * @return
     */
    RequestMethod[] method() default {};
}
