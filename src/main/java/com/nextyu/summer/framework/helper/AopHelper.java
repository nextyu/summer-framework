package com.nextyu.summer.framework.helper;

import com.nextyu.summer.framework.annotation.Aspect;
import com.nextyu.summer.framework.annotation.Service;
import com.nextyu.summer.framework.proxy.AspectProxy;
import com.nextyu.summer.framework.proxy.Proxy;
import com.nextyu.summer.framework.proxy.ProxyManager;
import com.nextyu.summer.framework.proxy.TransactionProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * 2017-06-25 下午1:29
 *
 * @author nextyu
 */
public final class AopHelper {

    private static Logger logger = LoggerFactory.getLogger(AopHelper.class);

    static {
        logger.debug("init aop helper");
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);

            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                // 目标类
                Class<?> targetClass = targetEntry.getKey();
                // 代理类实例
                List<Proxy> proxies = targetEntry.getValue();

                // 代理之后的对象
                Object proxy = ProxyManager.createProxy(targetClass, proxies);

                // 把容器里面以前的对象，替换为代理之后的对象
                BeanHelper.setBean(targetClass, proxy);
            }

        } catch (Exception e) {
            logger.error("aop failure", e);
        }
    }


    /**
     * 获取 Aspect 注解中 value 值（也是一个注解）标记的类，
     * 比如 @Aspect(Controller.class) 就是获取带有 Controller 注解的类
     *
     * @param aspect
     * @return
     * @throws Exception
     */
    private static Set<Class<?>> createTargetClasses(Aspect aspect) throws Exception {
        Set<Class<?>> targetClasses = new HashSet<>();
        Class<? extends Annotation> annotation = aspect.value();
        if (null != annotation && !Aspect.class.equals(annotation)) {
            targetClasses.addAll(ClassHelper.getClassesByAnnotation(annotation));
        }
        return targetClasses;
    }

    /**
     * 获取代理类和目标类集合之间的映射关系，一个代理类可以对应一个或多个目标类
     * 这里所说的目标类值得是切面类
     *
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> serviceClasses = ClassHelper.getClassesByAnnotation(Service.class);
        proxyMap.put(TransactionProxy.class, serviceClasses);
    }

    private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        // 代理类
        Set<Class<?>> proxyClasses = ClassHelper.getClassesBySuper(AspectProxy.class);
        for (Class<?> proxyClass : proxyClasses) {
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                // 目标类
                Set<Class<?>> targetClasses = createTargetClasses(aspect);
                proxyMap.put(proxyClass, targetClasses);
            }
        }
    }

    /**
     * 获取目标类和代理类实例集合之间的映射关系
     *
     * @param proxyMap
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            // 代理类
            Class<?> proxyClass = proxyEntry.getKey();
            // 目标类
            Set<Class<?>> targetClasses = proxyEntry.getValue();
            for (Class<?> targetClass : targetClasses) {
                // 代理类实例
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                } else {
                    List<Proxy> proxies = new ArrayList<>();
                    proxies.add(proxy);
                    targetMap.put(targetClass, proxies);
                }
            }
        }

        return targetMap;
    }
}
