package com.nextyu.summer.framework.proxy;

/**
 * 代理接口
 * created on 2017-06-23 16:58
 *
 * @author nextyu
 */
public interface Proxy {
    /**
     * 执行链式代理
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
