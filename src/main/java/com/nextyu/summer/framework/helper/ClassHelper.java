package com.nextyu.summer.framework.helper;

import com.nextyu.summer.framework.annotation.Controller;
import com.nextyu.summer.framework.annotation.RestController;
import com.nextyu.summer.framework.annotation.Service;
import com.nextyu.summer.framework.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * created on 2017-06-23 12:34
 *
 * @author nextyu
 */
public class ClassHelper {
    /**
     * 定义类集合（用于存放所加载的类）
     */
    private static final Set<Class<?>> CLASSES;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASSES = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取应用包名下的所有类
     */
    public static Set<Class<?>> getClasses() {
        return CLASSES;
    }


    /**
     * 获取应用包名下所有 Service 类
     *
     * @return
     */
    public static Set<Class<?>> getServiceClasses() {
        Set<Class<?>> classes = new HashSet<>();
        for (Class<?> aClass : CLASSES) {
            if (aClass.isAnnotationPresent(Service.class)) {
                classes.add(aClass);
            }
        }
        return classes;
    }

    /**
     * 获取应用包名下所有 Controller 类
     *
     * @return
     */
    public static Set<Class<?>> getControllerClasses() {
        Set<Class<?>> classes = new HashSet<>();
        for (Class<?> aClass : CLASSES) {
            if (aClass.isAnnotationPresent(RestController.class) || aClass.isAnnotationPresent(Controller.class)) {
                classes.add(aClass);
            }
        }
        return classes;
    }

    /**
     * 获取应用包名下所有 Bean 类（包括：Service、Controller 等）
     *
     * @return
     */
    public static Set<Class<?>> getBeanClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.addAll(getServiceClasses());
        classes.addAll(getControllerClasses());
        return classes;
    }

}
