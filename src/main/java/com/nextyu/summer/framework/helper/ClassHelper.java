package com.nextyu.summer.framework.helper;

import com.nextyu.summer.framework.annotation.Controller;
import com.nextyu.summer.framework.annotation.RestController;
import com.nextyu.summer.framework.annotation.Service;
import com.nextyu.summer.framework.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * created on 2017-06-23 12:34
 *
 * @author nextyu
 */
public final class ClassHelper {

    private static Logger logger = LoggerFactory.getLogger(ClassHelper.class);

    /**
     * 定义类集合（用于存放所加载的类）
     */
    private static final Set<Class<?>> CLASSES;

    static {
        logger.debug("init class helper");
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

    /**
     * 获取应用包名下某父类（或接口）的所有子类（或实现类）
     *
     * @param supperClass
     * @return
     */
    public static Set<Class<?>> getClassesBySuper(Class<?> supperClass) {
        Set<Class<?>> classes = new HashSet<>();
        for (Class<?> aClass : CLASSES) {
            if (supperClass.isAssignableFrom(aClass) && !supperClass.equals(aClass)) {
                classes.add(aClass);
            }
        }
        return classes;
    }

    /**
     * 获取应用包名下带有某注解的所有类
     *
     * @param annotationClass
     * @return
     */
    public static Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classes = new HashSet<>();
        for (Class<?> aClass : CLASSES) {
            if (aClass.isAnnotationPresent(annotationClass)) {
                classes.add(aClass);
            }
        }
        return classes;
    }

}
