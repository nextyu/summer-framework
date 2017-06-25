package com.nextyu.summer.framework.helper;

import com.nextyu.summer.framework.annotation.RequestMapping;
import com.nextyu.summer.framework.annotation.RequestMethod;
import com.nextyu.summer.framework.bean.Handler;
import com.nextyu.summer.framework.bean.Request;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手类
 * created on 2017-06-23 13:08
 *
 * @author nextyu
 */
public class ControllerHelper {
    private static Logger logger = LoggerFactory.getLogger(ControllerHelper.class);

    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static {
        logger.debug("init controller helper");
        Set<Class<?>> controllerClasses = ClassHelper.getControllerClasses();
        if (CollectionUtils.isNotEmpty(controllerClasses)) {
            for (Class<?> controllerClass : controllerClasses) {
                Method[] methods = controllerClass.getDeclaredMethods();
                if (ArrayUtils.isNotEmpty(methods)) {
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                            String requestPath = requestMapping.name();
                            RequestMethod[] requestMethod = requestMapping.method();
                            Request request = new Request(requestMethod, requestPath);
                            Handler handler = new Handler(controllerClass, method);
                            ACTION_MAP.put(request, handler);
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取 Handler
     */
    public static Handler getHandler(RequestMethod[] requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }

}
