package com.nextyu.summer.framework;

import com.nextyu.summer.framework.helper.*;
import com.nextyu.summer.framework.util.ClassUtil;

/**
 * 加载相应的 Helper 类
 * created on 2017-06-23 13:23
 *
 * @author nextyu
 */
public class HelperLoader {
    public static void init() {
        Class<?>[] classes = {
                ClassHelper.class
                , BeanHelper.class
                , AopHelper.class
                , IocHelper.class
                , ControllerHelper.class};
        for (Class<?> aClass : classes) {
            ClassUtil.loadClass(aClass.getName());
        }
    }
}
